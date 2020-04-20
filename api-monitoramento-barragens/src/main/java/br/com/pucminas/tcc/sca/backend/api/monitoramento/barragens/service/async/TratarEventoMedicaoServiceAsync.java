package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.async;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.BarragemService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.EventoMedicaoClassificacaoAlertaService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.IncidenteService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.SensorService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.BarragemDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoClassificacaoAlertaDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.SensorDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external.DadosExecucaoPlanoAcaoDTO;

@Service
public class TratarEventoMedicaoServiceAsync {

	private final Logger log = LoggerFactory.getLogger(TratarEventoMedicaoServiceAsync.class);
	
	private final SensorService sensorService; 
	private final IncidenteService incidenteService; 
	private final BarragemService barragemService;
	private final EventoMedicaoClassificacaoAlertaService eventoMedicaoClassificacaoAlertaService;
	
	private void registrarIncidente(EventoMedicaoDTO eventoMedicaoDTO, BarragemDTO barragemDTO) {
    	try {
    		IncidenteDTO incidenteDTO = new IncidenteDTO();
        	incidenteDTO.setBarragemId(barragemDTO.getId());
        	incidenteDTO.setBarragemNome(barragemDTO.getNome());
        	incidenteDTO.setClassificacao(eventoMedicaoDTO.getIntensidade());
        	incidenteDTO.setData(eventoMedicaoDTO.getData());
        	
        	incidenteService.save(incidenteDTO);
        	
        	// Dados a serem enviados para a api de segurança e comunicação (Dados classificação, barragem e afetados relacionados)
        	DadosExecucaoPlanoAcaoDTO dadosExecucaoPlanoAcaoDTO = new DadosExecucaoPlanoAcaoDTO();
        	dadosExecucaoPlanoAcaoDTO.setIdentificador(incidenteDTO.getIdentificador());
        	dadosExecucaoPlanoAcaoDTO.setClassificacao(eventoMedicaoDTO.getIntensidade());
        	dadosExecucaoPlanoAcaoDTO.setBarragem(barragemDTO);
        	
        	incidenteService.executarPlanoAcao(dadosExecucaoPlanoAcaoDTO);
    	} catch (Exception e) {
    		log.error("Erro ao registrar Incidente:  {}", e.getMessage());
    	}
    }	
	
	private void tratarEventoMedicao(EventoMedicaoDTO eventoMedicaoDTO) {
		try {
			// Verificar se evento excede limites de segurança

			Optional<EventoMedicaoClassificacaoAlertaDTO> optEventoMedicaoClassificacaoAlertaDTO = eventoMedicaoClassificacaoAlertaService
					.findByTipoMedicaoAndByItensidade(
							eventoMedicaoDTO
								.getTipo(), 
							eventoMedicaoDTO
								.getIntensidade()
							);		

			if (optEventoMedicaoClassificacaoAlertaDTO.isPresent()) {

				if (optEventoMedicaoClassificacaoAlertaDTO.get().isDispararAlertas()) {

					switch (optEventoMedicaoClassificacaoAlertaDTO.get().getTipo()) {
						case NIVEL_ARMAZENAMENTO: {
							log.info("Evento de medição de Nível de Armazenamento acima do esperado : {}", eventoMedicaoDTO.getIntensidade());
							break;
						}
						case PRESSAO: {
							log.info("Evento de medição de Pressão acima do esperado : {}", eventoMedicaoDTO.getIntensidade());
							break;
						}
						case TREMORES: {
							log.info("Evento de medição de Tremor acima do esperado : {}", eventoMedicaoDTO.getIntensidade());
							break;
						}
						case GENERICO: {
							log.debug("Evento de medição Genérico acima do esperado : {}", eventoMedicaoDTO.getIntensidade());
							break;
						}
						default:
							log.debug("Evento de medição acima do esperado : {}", eventoMedicaoDTO.getIntensidade());
							break;
					}

					// Realizar chamada de api para microserviço de segurança e comunicação,
					// enviando os dados da barragem relacionada ao sensor
					// Localizar o sensor e barragem e afetados relacionados
					// Montar DTO de requisição para o microserviço de Segurança e Comunicação
					
					SensorDTO sensorDTO;
					sensorDTO = sensorService
							.findByIdentificador(
									eventoMedicaoDTO
										.getSensorIdentificador()
									)
							.get();

					//log.info("Código/Nome Barragem : {}", sensorDTO.getBarragemId() + " / " + sensorDTO.getBarragemNome());
					
					BarragemDTO barragemDTO;
					barragemDTO = barragemService.findOne(sensorDTO.getBarragemId()).get();
																								
					this.registrarIncidente(eventoMedicaoDTO, barragemDTO);
				} else {
					log.info("Classificação prévia para disparar alertas com Tipo/Insentidade : {} não está definida para solicitar envio de altertas.",
							eventoMedicaoDTO.getIntensidade() + " / " + eventoMedicaoDTO.getTipo());
				}
			} else {
				log.info("Não foi encontrada uma classificação prévia para disparar alertas com Tipo/Insentidade : {}",
						eventoMedicaoDTO.getIntensidade() + " / " + eventoMedicaoDTO.getTipo());
			}
		} catch (Exception e) {
			log.error("Erro ao tratar Evendo de Medição:  {}", e.getMessage());
		}
	}
	
	public TratarEventoMedicaoServiceAsync(SensorService sensorService, IncidenteService incidenteService, BarragemService barragemService, EventoMedicaoClassificacaoAlertaService eventoMedicaoClassificacaoAlertaService) {
		this.sensorService = sensorService;
		this.incidenteService = incidenteService;
		this.barragemService = barragemService;
		this.eventoMedicaoClassificacaoAlertaService = eventoMedicaoClassificacaoAlertaService;
	}
	
	@Async("taskExecutorSecurityContext")
	public void executeAsync(EventoMedicaoDTO eventoMedicaoDTO) {
		try {
			this.tratarEventoMedicao(eventoMedicaoDTO);
		} catch (Exception e) {
			log.error("Erro ao tratar Evendo de Medição:  {}", e.getMessage());
		}
	}
}
