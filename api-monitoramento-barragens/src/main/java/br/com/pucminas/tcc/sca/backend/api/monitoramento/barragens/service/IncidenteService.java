package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.config.ApplicationProperties;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Incidente;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.IncidenteRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.security.SecurityUtils;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteResultadoProcessamentoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external.DadosExecucaoPlanoAcaoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.IncidenteMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link Incidente}.
 */
@Service
@Transactional
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class IncidenteService {

    private final Logger log = LoggerFactory.getLogger(IncidenteService.class);

    private final IncidenteRepository incidenteRepository;

    private final IncidenteMapper incidenteMapper;
    
    //@Autowired
    private final ApplicationProperties applicationProperties;
    
    private final IncidenteResultadoProcessamentoService incidenteResultadoProcessamentoService;

    
    private void registrarIncidenteResultadoProcessamento(DadosExecucaoPlanoAcaoDTO dadosExecucaoPlanoAcaoDTO, Boolean sucesso, String mensagem) {
    	try {
    		IncidenteResultadoProcessamentoDTO incidenteResultadoProcessamentoDTO = new IncidenteResultadoProcessamentoDTO();
        	incidenteResultadoProcessamentoDTO.setData(Instant.now());
        	incidenteResultadoProcessamentoDTO.setIncidenteClassificacao(dadosExecucaoPlanoAcaoDTO.getClassificacao());
        	incidenteResultadoProcessamentoDTO.setIncidenteIdentificador(dadosExecucaoPlanoAcaoDTO.getIdentificador());
        	incidenteResultadoProcessamentoDTO.setMensagem(mensagem);
        	incidenteResultadoProcessamentoDTO.setSucesso(true);
        	this.incidenteResultadoProcessamentoService.save(incidenteResultadoProcessamentoDTO);
    	} catch (Exception e) {
    		
    	}
    }
    
    public IncidenteService(IncidenteRepository incidenteRepository, IncidenteMapper incidenteMapper, ApplicationProperties applicationProperties,
    		IncidenteResultadoProcessamentoService incidenteResultadoProcessamentoService) {
        this.incidenteRepository = incidenteRepository;
        this.incidenteMapper = incidenteMapper;
        this.applicationProperties = applicationProperties;
        this.incidenteResultadoProcessamentoService = incidenteResultadoProcessamentoService;
    }

    /**
     * Save a incidente.
     *
     * @param incidenteDTO the entity to save.
     * @return the persisted entity.
     */
    public IncidenteDTO save(IncidenteDTO incidenteDTO) {
        log.debug("Request to save Incidente : {}", incidenteDTO);
        
        // Gerar identificador único (UUID) para o incidente recebido
        if (incidenteDTO.getIdentificador() == null || incidenteDTO.getIdentificador().toString().equals("00000000-0000-0000-0000-000000000000")) {
        	incidenteDTO.setIdentificador(UUID.randomUUID());
        }         
        
        Incidente incidente = incidenteMapper.toEntity(incidenteDTO);        
        incidente = incidenteRepository.save(incidente);
        return incidenteMapper.toDto(incidente);
    }

    /**
     * Get all the incidentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IncidenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Incidentes");
        return incidenteRepository.findAll(pageable)
            .map(incidenteMapper::toDto);
    }

    /**
     * Get one incidente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IncidenteDTO> findOne(Long id) {
        log.debug("Request to get Incidente : {}", id);
        return incidenteRepository.findById(id)
            .map(incidenteMapper::toDto);
    }

    /**
     * Delete the incidente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Incidente : {}", id);
        incidenteRepository.deleteById(id);
    }
    
    public void executarPlanoAcao(DadosExecucaoPlanoAcaoDTO dadosExecucaoPlanoAcaoDTO) {
    	String mensagem = "Sem log de resultado da solicitação.";
    	Boolean sucessoRequisicao = false;
    	try {
        	final String uri = applicationProperties
        			.getTCCSca()
        			.getServicesURL()
        			.getApiSeguranca() + "/api/planos-acao/executar";
        	
        	//log.info("URI: {}", uri);
        	
    		Optional<String> accessToken = SecurityUtils.getCurrentUserJWT();
        	if (accessToken.isPresent()) {
        		
        		HttpHeaders httpHeaders = new HttpHeaders();
        		httpHeaders.setAccept(Arrays.asList(MediaType.ALL));
            	httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            	//httpHeaders.set("Accept-Encoding", "gzip, deflate, br");
            	//httpHeaders.set("Connection", "keep-alive");        	
            	httpHeaders.setBearerAuth(accessToken.get());
            	                                        	
            	HttpEntity<DadosExecucaoPlanoAcaoDTO> entity = new HttpEntity<>(dadosExecucaoPlanoAcaoDTO, httpHeaders);
           
            	RestTemplate restTemplate = new RestTemplateBuilder()
            			.setConnectTimeout(Duration.ofSeconds(500))
            			.setReadTimeout(Duration.ofSeconds(500))
            			.build();
            	
            	ResponseEntity<String> response = restTemplate
            			.exchange(
            					uri, 
            					HttpMethod.POST,             					
            					entity,
            					String.class
            				);
            			                                
            	log.info("Resultado requisição: {}", response.getBody());
            	mensagem = "Resultado requisição: \n" + response.getBody();
            	sucessoRequisicao = (response.getStatusCode() == HttpStatus.ACCEPTED) ? true : false;
        	} else {
        		log.error("Resultado requisição: {}", "Não foi possível obter autorização para a requisição");            	
        		mensagem = "Resultado requisição: \n" + "Não foi possível obter autorização para a requisição";
        	} 
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		mensagem = "Resultado requisição: \n" + e.getMessage();
    	} finally {
    		this.registrarIncidenteResultadoProcessamento(
    				dadosExecucaoPlanoAcaoDTO, 
    				sucessoRequisicao, 
    				mensagem
    			);
    	}
    }
}
