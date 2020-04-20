package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.IncidenteResultadoProcessamento;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.IncidenteResultadoProcessamentoRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteResultadoProcessamentoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.IncidenteResultadoProcessamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IncidenteResultadoProcessamento}.
 */
@Service
@Transactional
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class IncidenteResultadoProcessamentoService {

    private final Logger log = LoggerFactory.getLogger(IncidenteResultadoProcessamentoService.class);

    private final IncidenteResultadoProcessamentoRepository incidenteResultadoProcessamentoRepository;

    private final IncidenteResultadoProcessamentoMapper incidenteResultadoProcessamentoMapper;

    public IncidenteResultadoProcessamentoService(IncidenteResultadoProcessamentoRepository incidenteResultadoProcessamentoRepository, IncidenteResultadoProcessamentoMapper incidenteResultadoProcessamentoMapper) {
        this.incidenteResultadoProcessamentoRepository = incidenteResultadoProcessamentoRepository;
        this.incidenteResultadoProcessamentoMapper = incidenteResultadoProcessamentoMapper;
    }

    /**
     * Save a incidenteResultadoProcessamento.
     *
     * @param incidenteResultadoProcessamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public IncidenteResultadoProcessamentoDTO save(IncidenteResultadoProcessamentoDTO incidenteResultadoProcessamentoDTO) {
        log.debug("Request to save IncidenteResultadoProcessamento : {}", incidenteResultadoProcessamentoDTO);
        IncidenteResultadoProcessamento incidenteResultadoProcessamento = incidenteResultadoProcessamentoMapper.toEntity(incidenteResultadoProcessamentoDTO);
        incidenteResultadoProcessamento = incidenteResultadoProcessamentoRepository.save(incidenteResultadoProcessamento);
        return incidenteResultadoProcessamentoMapper.toDto(incidenteResultadoProcessamento);
    }

    /**
     * Get all the incidenteResultadoProcessamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IncidenteResultadoProcessamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncidenteResultadoProcessamentos");
        return incidenteResultadoProcessamentoRepository.findAll(pageable)
            .map(incidenteResultadoProcessamentoMapper::toDto);
    }

    /**
     * Get one incidenteResultadoProcessamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IncidenteResultadoProcessamentoDTO> findOne(Long id) {
        log.debug("Request to get IncidenteResultadoProcessamento : {}", id);
        return incidenteResultadoProcessamentoRepository.findById(id)
            .map(incidenteResultadoProcessamentoMapper::toDto);
    }

    /**
     * Delete the incidenteResultadoProcessamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IncidenteResultadoProcessamento : {}", id);
        incidenteResultadoProcessamentoRepository.deleteById(id);
    }
}
