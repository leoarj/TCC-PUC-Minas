package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicaoClassificacaoAlerta;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.EventoMedicaoClassificacaoAlertaRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoClassificacaoAlertaDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.EventoMedicaoClassificacaoAlertaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EventoMedicaoClassificacaoAlerta}.
 */
@Service
@Transactional
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class EventoMedicaoClassificacaoAlertaService {

    private final Logger log = LoggerFactory.getLogger(EventoMedicaoClassificacaoAlertaService.class);

    private final EventoMedicaoClassificacaoAlertaRepository eventoMedicaoClassificacaoAlertaRepository;

    private final EventoMedicaoClassificacaoAlertaMapper eventoMedicaoClassificacaoAlertaMapper;

    public EventoMedicaoClassificacaoAlertaService(EventoMedicaoClassificacaoAlertaRepository eventoMedicaoClassificacaoAlertaRepository, EventoMedicaoClassificacaoAlertaMapper eventoMedicaoClassificacaoAlertaMapper) {
        this.eventoMedicaoClassificacaoAlertaRepository = eventoMedicaoClassificacaoAlertaRepository;
        this.eventoMedicaoClassificacaoAlertaMapper = eventoMedicaoClassificacaoAlertaMapper;
    }

    /**
     * Save a eventoMedicaoClassificacaoAlerta.
     *
     * @param eventoMedicaoClassificacaoAlertaDTO the entity to save.
     * @return the persisted entity.
     */
    public EventoMedicaoClassificacaoAlertaDTO save(EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO) {
        log.debug("Request to save EventoMedicaoClassificacaoAlerta : {}", eventoMedicaoClassificacaoAlertaDTO);
        EventoMedicaoClassificacaoAlerta eventoMedicaoClassificacaoAlerta = eventoMedicaoClassificacaoAlertaMapper.toEntity(eventoMedicaoClassificacaoAlertaDTO);
        eventoMedicaoClassificacaoAlerta = eventoMedicaoClassificacaoAlertaRepository.save(eventoMedicaoClassificacaoAlerta);
        return eventoMedicaoClassificacaoAlertaMapper.toDto(eventoMedicaoClassificacaoAlerta);
    }

    /**
     * Get all the eventoMedicaoClassificacaoAlertas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventoMedicaoClassificacaoAlertaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventoMedicaoClassificacaoAlertas");
        return eventoMedicaoClassificacaoAlertaRepository.findAll(pageable)
            .map(eventoMedicaoClassificacaoAlertaMapper::toDto);
    }

    /**
     * Get one eventoMedicaoClassificacaoAlerta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventoMedicaoClassificacaoAlertaDTO> findOne(Long id) {
        log.debug("Request to get EventoMedicaoClassificacaoAlerta : {}", id);
        return eventoMedicaoClassificacaoAlertaRepository.findById(id)
            .map(eventoMedicaoClassificacaoAlertaMapper::toDto);
    }

    /**
     * Delete the eventoMedicaoClassificacaoAlerta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventoMedicaoClassificacaoAlerta : {}", id);
        eventoMedicaoClassificacaoAlertaRepository.deleteById(id);
    }
    
    /**
     * Get one eventoMedicaoClassificacaoAlerta by Tipo Medição e Intensidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<EventoMedicaoClassificacaoAlertaDTO> findByTipoMedicaoAndByItensidade(TipoMedicao tipoMedicao, Integer intensidade) {
    	log.debug("Request to get EventoMedicaoClassificacaoAlerta : {}", tipoMedicao + " / " + intensidade);
        return eventoMedicaoClassificacaoAlertaRepository.findByTipoMedicaoAndByItensidade(tipoMedicao, intensidade)
            .map(eventoMedicaoClassificacaoAlertaMapper::toDto);
    }
}
