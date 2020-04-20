//-----------------------------------------------------------------------------------------------------------
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.
// This application uses the Azure IoT Hub device SDK for Java
// For samples see: https://github.com/Azure/azure-iot-sdk-java/tree/master/device/iot-device-samples
//-----------------------------------------------------------------------------------------------------------
// Esta classe foi baseada em documentação do SDK para Java do Azure Hub IoT fornecido pela Microsoft.
// Documentação: https://docs.microsoft.com/pt-br/azure/iot-hub/quickstart-send-telemetry-java.
//
// Faz parte do TCC do curso de "Especialização em Arquitetura de Software Distribuído" pela PUC Minas.
// Aluno: Leandro Araújo - (leandro.araujo@outlook.com)
//-----------------------------------------------------------------------------------------------------------

package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.external.azure.iothub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import com.microsoft.azure.eventhubs.EventPosition;
import com.microsoft.azure.eventhubs.EventHubRuntimeInformation;
import com.microsoft.azure.eventhubs.PartitionReceiver;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.EventoMedicaoService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external.EventoMedicaoResumoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.exceptions.InvalidSecurityContextSetAuthentication;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * Esta classe é um receptor de eventos que foram enviados ao Azure Hub IoT por dispotivos que operam com o protocolo MQTT.
 * Utiliza componentes do SDK fornecido pela Microsoft e se comunica com o serviço recebendo lotes de dados.
 * Nessa classe são recebidos eventos de medição de barragens, com dados simulados por um sensor virtual.
 * 
 * A classe reutiliza as regras de negócio providas pela camada de serviço da aplicação para realizar a gravação
 * do evento de medição, registro de incidente e comunicação com a API de segurança e comunicação para envio de alertas.
 * A classe se utiliza de processamento assíncrono.
 * 
 * Está implementando a interface {@link ApplicationListener} tipada com {@link ApplicationReadyEvent} para inicializar somente após
 * a aplicação estar completamente carregada e configurada.
 * Está implementando {@link DisposableBean} para liberar os recursos alocados pelo receptor antes da destruição do objeto.
 * 
 * @author Leandro Araújo (leandro.araujo@outlook.com)
 *
 */
@Component
public class ReceptorEventoMedicaoAzureIoTHubService
		implements ApplicationListener<ApplicationReadyEvent>, DisposableBean {

	private final Logger log = LoggerFactory.getLogger(ReceptorEventoMedicaoAzureIoTHubService.class);

	// Para obter os valores de conexão, os comandos abaixo devem ser executados no "Azure Cloud Shell"
	// em produção armazenar esses valores e recuperar de forma segura com servidor de configuração
	
	// az iot hub show --query properties.eventHubEndpoints.events.endpoint --name
	// {your IoT Hub name/Endpoint do Hub IoT}
	private static final String eventHubsCompatibleEndpoint = "sb://iothub-ns----servicebus.windows.net/";

	// az iot hub show --query properties.eventHubEndpoints.events.path --name {your
	// IoT Hub name/Nome do Hub IoT}
	private static final String eventHubsCompatiblePath = "";

	// az iot hub policy show --name service --query primaryKey --hub-name {your IoT
	// Hub name/Chave de API e nome da Chave}
	private static final String iotHubSasKey = "";
	private static final String iotHubSasKeyName = "service";

	// Track all the PartitionReciever instances created.
	//
	// Reastrear todas as instâncias de receptores de partição criados.
	private ArrayList<PartitionReceiver> receivers = new ArrayList<PartitionReceiver>();

	private ExecutorService executorService;
	private EventHubClient eventHubClient;

	private final EventoMedicaoService eventoMedicaoService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	private ObjectMapper objectMapper;
	
	private Authentication authentication;	
	
	public ReceptorEventoMedicaoAzureIoTHubService(EventoMedicaoService eventoMedicaoService) {
		this.eventoMedicaoService = eventoMedicaoService;
		this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	}
	
	// Asynchronously create a PartitionReceiver for a partition and then start
	// reading any messages sent from the simulated client.
	//
	// Cria assincronamente a partição de receptores e inicia a leitura das mensagems
	// enviadas a partir do dispositivo simulado.
	private void receiveMessages(EventHubClient ehClient, String partitionId)
			throws EventHubException, ExecutionException, InterruptedException {

		final ExecutorService executorService = Executors.newSingleThreadExecutor();
		log.info("delegatingSecurityContextExecutorServiceAzureIoT");

		// Create the receiver using the default consumer group.
		// For the purposes of this sample, read only messages sent since
		// the time the receiver is created. Typically, you don't want to skip any
		// messages.
		//
		// Cria o receptor usando o grupo de consumo/consumidores padrão.
		// Em ambiente de produção um requisto é não desconsiderar eventos
		// não tratados com timestamp anterior a data atual.
		ehClient.createReceiver(
				EventHubClient.DEFAULT_CONSUMER_GROUP_NAME, 
				partitionId,
				EventPosition
					.fromEnqueuedTime(
							Instant.now()
						)
				)
			.thenAcceptAsync(receiver -> {

					receivers.add(receiver);

					while (true) {
						try {
							// Check for EventData - this methods times out if there is nothing to retrieve.
							//
							// Verifica dados do Evento - Este método expira caso não houver dados para recuperar.
							Iterable<EventData> receivedEvents = receiver.receiveSync(100);

							if (receivedEvents != null) {
								for (EventData receivedEvent : receivedEvents) {

									try {
										// Telemetria enviada é igual a dto de resumo de eventos, deserializar.
										EventoMedicaoResumoDTO eventoMedicaoResumoDTO = objectMapper
												.readValue(
														receivedEvent
															.getBytes(),
														EventoMedicaoResumoDTO.class
													);
										
										//log.debug("eventoMedicaoResumoDTO : {}", eventoMedicaoResumoDTO);

										if (eventoMedicaoResumoDTO != null) {

											if ((eventoMedicaoResumoDTO.getSensorIdentificador() == null)
													|| (eventoMedicaoResumoDTO.getIntensidade() == 0)
													|| (eventoMedicaoResumoDTO.getTipo() == null)) {
												continue;
											}

											/**log.info("Azure Hub - Sensor Identificador : {}", eventoMedicaoResumoDTO.getSensorIdentificador());
											log.info("Azure Hub - Intensidade : {}", eventoMedicaoResumoDTO.getIntensidade());
											log.info("Azure Hub - Tipo : {}", eventoMedicaoResumoDTO.getTipo());
											log.info("Azure Hub - Data : {}", eventoMedicaoResumoDTO.getData());*/

											EventoMedicaoDTO eventoMedicaoDTO = new EventoMedicaoDTO();
											eventoMedicaoDTO.setSensorIdentificador(eventoMedicaoResumoDTO.getSensorIdentificador());
											eventoMedicaoDTO.setIntensidade(eventoMedicaoResumoDTO.getIntensidade());
											eventoMedicaoDTO.setTipo(eventoMedicaoResumoDTO.getTipo());
											eventoMedicaoDTO.setData(eventoMedicaoResumoDTO.getData());
											
											// Dependendo da classificação do evento dispara regras de negócio específicas.
											// Deve atualizar a autenticação do contexto antes (da thread) chamar a camada de serviço.
											SecurityContextHolder.getContext().setAuthentication(this.authentication);
											eventoMedicaoService.save(eventoMedicaoDTO);
										}
									} catch (Exception e) {
										log.error("Azure Hub - Iot - Erro ao ler dados de evento : {}", e.getMessage());
									}
								}
							}
						} catch (EventHubException e) {
							log.error("Azure Hub - Iot - Erro ao ler evento : {}", e.getMessage());
						}
					}
				}, executorService);
	}

	private void inicializarReceptor() {
		try {
			log.info("Azure Hub - Iot - Inicializando de Receptor de eventos.");
						
			if (authenticationManager != null) {
				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken("admin", "admin"); // Recuperar de forma segura
				this.authentication = authenticationManager.authenticate(authReq);
				SecurityContextHolder.getContext().setAuthentication(this.authentication);
				
				// Repassa a autenticação para que processos assíncronos da camada de serviço tenham acesso ao token e
				// possam montar o header da requisição.
				try {
					eventoMedicaoService.setSecurityContextAuthentication(this.authentication);
				} catch (InvalidSecurityContextSetAuthentication e) {
					log.error("Azure Hub - Iot - Erro ao transferir contexto de segurança : {}", e.getMessage());
					return;
				}
				
				//log.info("Azure Hub - Iot - Microserviço autenticado: {}", this.authentication.getCredentials());
			} else {
				log.error("Azure Hub - Iot - Erro ao inicializar receptor eventos : {}", "Autenticador nulo");
				return;
			}
			
			final ConnectionStringBuilder connStr = new ConnectionStringBuilder()
					.setEndpoint(
							new URI(eventHubsCompatibleEndpoint)
								)
					.setEventHubName(eventHubsCompatiblePath)
					.setSasKeyName(iotHubSasKeyName)
					.setSasKey(iotHubSasKey);

			// Create an EventHubClient instance to connect to the
			// IoT Hub Event Hubs-compatible endpoint.
			//
			// Cria o Cliente do Hub de Eventos para se conectar
			// com o endpoint compatível dos Hubs de Evento IoT
			final ExecutorService executorService = Executors.newSingleThreadExecutor();
			final EventHubClient ehClient = EventHubClient.createSync(connStr.toString(), executorService);

			// Use the EventHubRunTimeInformation to find out how many partitions
			// there are on the hub.
			//
			// Usa as informações do Hub tem tempo de execução para encontrar quantas
			// partições estão no Hub.
			final EventHubRuntimeInformation eventHubInfo = ehClient.getRuntimeInformation().get();

			this.eventHubClient = ehClient;
			this.executorService = executorService;

			log.info("Azure Hub - Iot - Receptor de eventos inicializado.");
			
			// Create a PartitionReciever for each partition on the hub.
			//
			// Cria um Receptor de Partição para cada partição presente no Hub.
			for (String partitionId : eventHubInfo.getPartitionIds()) {
				this.receiveMessages(ehClient, partitionId);
			}			
		} catch (Exception e) {
			log.error("Azure Hub - Iot - Erro ao inicializar receptor de eventos : {}", e.getMessage());
		}
	}

	// https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html#destroyMethod--
	// Para executar na destruição do objeto, deve ser recuperado como @Bean

	// Pode ser shutdown() também caso não precise implementar DisposableBean
	// public void shutdown() {
	private void finalizarReceptor() {
		log.info("Azure Hub - Iot - Finalizando receptor de eventos.");

		if (this.receivers != null) {
			for (PartitionReceiver receiver : this.receivers) {
				try {
					receiver.closeSync();
				} catch (Exception e) {
					log.error("Azure Hub - Iot - Erro ao finalizar receptor : {}", e.getMessage());
					continue;
				}
			}
		}

		if (this.eventHubClient != null) {
			try {
				this.eventHubClient.closeSync();
			} catch (Exception e) {
				log.error("Azure Hub - Iot - Erro ao finalizar client do Hub : {}", e.getMessage());
			}
		}

		if (this.executorService != null) {
			try {
				this.executorService.shutdown();
			} catch (Exception e) {
				log.error("Azure Hub - Iot - Erro ao finalizar Executor Service : {}", e.getMessage());
			}
		}
		log.info("Azure Hub - Iot - Receptor de eventos finalizado.");
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		this.inicializarReceptor();
	}

	@Override
	public void destroy() throws Exception {
		this.finalizarReceptor();
	}

}
