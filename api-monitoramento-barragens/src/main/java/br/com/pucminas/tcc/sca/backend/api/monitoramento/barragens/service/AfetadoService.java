package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Afetado;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.AfetadoRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.AfetadoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.AfetadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Afetado}.
 */
@Service
@Transactional
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class AfetadoService {

    private final Logger log = LoggerFactory.getLogger(AfetadoService.class);

    private final AfetadoRepository afetadoRepository;

    private final AfetadoMapper afetadoMapper;

    public AfetadoService(AfetadoRepository afetadoRepository, AfetadoMapper afetadoMapper) {
        this.afetadoRepository = afetadoRepository;
        this.afetadoMapper = afetadoMapper;
    }

    /**
     * Save a afetado.
     *
     * @param afetadoDTO the entity to save.
     * @return the persisted entity.
     */
    public AfetadoDTO save(AfetadoDTO afetadoDTO) {
        log.debug("Request to save Afetado : {}", afetadoDTO);
        Afetado afetado = afetadoMapper.toEntity(afetadoDTO);
        afetado = afetadoRepository.save(afetado);
        return afetadoMapper.toDto(afetado);
    }

    /**
     * Get all the afetados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AfetadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Afetados");
        return afetadoRepository.findAll(pageable)
            .map(afetadoMapper::toDto);
    }

    /**
     * Get one afetado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AfetadoDTO> findOne(Long id) {
        log.debug("Request to get Afetado : {}", id);
        return afetadoRepository.findById(id)
            .map(afetadoMapper::toDto);
    }

    /**
     * Delete the afetado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Afetado : {}", id);
        afetadoRepository.deleteById(id);
    }
}
