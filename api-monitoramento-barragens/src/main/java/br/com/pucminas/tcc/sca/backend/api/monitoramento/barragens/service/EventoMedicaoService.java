package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicao;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.EventoMedicaoRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.async.TratarEventoMedicaoServiceAsync;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.exceptions.InvalidSecurityContextSetAuthentication;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.EventoMedicaoMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link EventoMedicao}.
 */
@Service
@Transactional
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class EventoMedicaoService {

    private final Logger log = LoggerFactory.getLogger(EventoMedicaoService.class);

    private final EventoMedicaoRepository eventoMedicaoRepository;

    private final EventoMedicaoMapper eventoMedicaoMapper;
        
    private final TratarEventoMedicaoServiceAsync tratarEventoMedicaoServiceAsync;
    
    public EventoMedicaoService(EventoMedicaoRepository eventoMedicaoRepository, EventoMedicaoMapper eventoMedicaoMapper, TratarEventoMedicaoServiceAsync tratarEventoMedicaoServiceAsync) {
        this.eventoMedicaoRepository = eventoMedicaoRepository;
        this.eventoMedicaoMapper = eventoMedicaoMapper;
        this.tratarEventoMedicaoServiceAsync = tratarEventoMedicaoServiceAsync;   
    }

    /**
     * Save a eventoMedicao.
     *
     * @param eventoMedicaoDTO the entity to save.
     * @return the persisted entity.
     */
    public EventoMedicaoDTO save(EventoMedicaoDTO eventoMedicaoDTO) {
        log.debug("Request to save EventoMedicao : {}", eventoMedicaoDTO);
        
        // Gerar identificador único (UUID) para o evento recebido
        if (eventoMedicaoDTO.getIdentificador() == null || eventoMedicaoDTO.getIdentificador().toString().equals("00000000-0000-0000-0000-000000000000")) {
        	eventoMedicaoDTO.setIdentificador(UUID.randomUUID());
        } 
        
        if (eventoMedicaoDTO.getData() == null) {
        	eventoMedicaoDTO.setData(Instant.now());
        }
        
        EventoMedicao eventoMedicao = eventoMedicaoMapper.toEntity(eventoMedicaoDTO);        
        eventoMedicao = eventoMedicaoRepository.save(eventoMedicao);
        
        try {        	
        	tratarEventoMedicaoServiceAsync.executeAsync(eventoMedicaoDTO);
        } catch(Exception e) {
        	log.error("Falha ao criar o processo de tratamento do Evento de Medição : {}", e.getMessage());
        }
                                       
        return eventoMedicaoMapper.toDto(eventoMedicao);
    }
    
    /**
     * Get all the eventoMedicaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventoMedicaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventoMedicaos");
        return eventoMedicaoRepository.findAll(pageable)
            .map(eventoMedicaoMapper::toDto);
    }

    /**
     * Get one eventoMedicao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventoMedicaoDTO> findOne(Long id) {
        log.debug("Request to get EventoMedicao : {}", id);
        return eventoMedicaoRepository.findById(id)
            .map(eventoMedicaoMapper::toDto);
    }

    /**
     * Delete the eventoMedicao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventoMedicao : {}", id);
        eventoMedicaoRepository.deleteById(id);
    }
    
    public void setSecurityContextAuthentication(Authentication authentication) throws InvalidSecurityContextSetAuthentication {
    	if (authentication != null) {
    		SecurityContextHolder.getContext().setAuthentication(authentication);
    	} else {
    		throw new InvalidSecurityContextSetAuthentication("Objeto de autenticação para mudança de contexto de segurança não pode ser nulo");
    	}
    }
}
