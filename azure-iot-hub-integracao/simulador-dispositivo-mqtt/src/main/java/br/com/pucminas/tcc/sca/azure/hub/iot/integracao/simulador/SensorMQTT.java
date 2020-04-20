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

package br.com.pucminas.tcc.sca.azure.hub.iot.integracao.simulador;

import com.microsoft.azure.sdk.iot.device.*;
import com.google.gson.Gson;

import java.io.*;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
//import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SensorMQTT {
	// The device connection string to authenticate the device with your IoT hub.
	// Using the Azure CLI:
	// az iot hub device-identity show-connection-string --hub-name {YourIoTHubName}
	// --device-id MyJavaDevice --output tabl
	//
	// Chave de acesso/conexão para o dispositivo se conectar ao Hub IoT
	private static String connString = "HostName=tcc-sca-puc-minas-IoT-Hub.azure-devices.net;DeviceId=SensorTremores01;SharedAccessKey=";

	// Using the MQTT protocol to connect to IoT Hub
	//
	// Usando o protocolo MQTT para se conectar ao IoT Hub
	private static IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
	private static DeviceClient client;

	// Specify the telemetry to send to your IoT hub.
	//
	// Especifica a telemetria a ser enviada para o IoTHub.
	// Pode ser baseado em um dto especificado no backend
	// ou tratar a serialização dos dados ao receber o evento no sistema. 
	private static class TelemetriaSensorBarragem {
		public int intensidade;
		public String sensorIdentificador;
		public String tipo;
		public String data;

		// Serialize object to JSON format.
		//
		// Serializa para o formato JSON.
		public String serialize() {
			Gson gson = new Gson();
			return gson.toJson(this);
		}
	}

	// Print the acknowledgement received from IoT Hub for the telemetry message sent.
	//
	// Exibe no console a confirmação recebida do IoT Hub para a mensagem de telemetria enviada.
	private static class EventCallback implements IotHubEventCallback {
		public void execute(IotHubStatusCode status, Object context) {
			System.out.println("IoT Hub - Resposta com status: " + status.name());

			if (context != null) {
				synchronized (context) {
					context.notify();
				}
			}
		}
	}

	private static class MessageSender implements Runnable {
		public void run() {
			int totalEventos = 0;
			int intensidadeMinima = 40;
			try {
				// Initialize the simulated telemetry.
				//
				// Inicializar a telemetria simulada.				
				// Simples exemplo apenas para enviar diferentes tipos de Eventos de Medição.
				List<Integer> intensidades = new ArrayList<Integer>();
				List<String> tipos = new ArrayList<String>();

				intensidades.add(40);
				intensidades.add(100);

				tipos.add("TREMORES");
				tipos.add("NIVEL_ARMAZENAMENTO");

				while (totalEventos < 2) {
					// Simulate telemetry.
					//
					// Simular telemetria.
					
					TelemetriaSensorBarragem telemetriaSensorBarragem = new TelemetriaSensorBarragem();
					// Sensor cadastrado no sistema e associada a uma barragem
					telemetriaSensorBarragem.sensorIdentificador = "241d60e0-1bc6-4dbd-a462-a9c2aed7dbf0";
					telemetriaSensorBarragem.intensidade = intensidades.get(totalEventos);
					telemetriaSensorBarragem.tipo = tipos.get(totalEventos);
					telemetriaSensorBarragem.data = Instant.now().toString();

					// Add the telemetry to the message body as JSON.
					//
					// Adiciona a telemetria ao corpo da mensagem como JSON.
					String msgStr = telemetriaSensorBarragem.serialize();
					Message msg = new Message(msgStr);

					// Add a custom application property to the message.
					// An IoT hub can filter on these properties without access to the message body.
					//
					// Adiciona uma propriedade customizada a mensagem.
					// O IoT HUb pode realizar filtros com essas propriedades sem necessariamente ter acesso ao corpo da mensagem.
					msg.setProperty("alertaMedicao", (telemetriaSensorBarragem.intensidade >= intensidadeMinima) ? "true" : "false");

					System.out.println("Enviando telemetria: " + msgStr);

					Object lockobj = new Object();

					// Send the message.
					//
					// Envia a mensagem.
					EventCallback callback = new EventCallback();
					client.sendEventAsync(msg, callback, lockobj);

					synchronized (lockobj) {
						lockobj.wait();
					}
					Thread.sleep(1000);
					totalEventos++;
				}
			} catch (InterruptedException e) {
				System.out.println("Finalizado.");
			}
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {

		// Connect to the IoT hub.
		//
		// Conectar ao IoT Hub.
		client = new DeviceClient(connString, protocol);
		client.open();

		// Create new thread and start sending messages
		//
		// Criar uma nova thread e iniciar o envio das mensagens.
		MessageSender sender = new MessageSender();
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(sender);

		// Stop the application.
		//
		// Para a aplicação.
		System.out.println("Pressione ENTER para sair.");
		System.in.read();
		executor.shutdownNow();
		client.closeNow();
	}
}
