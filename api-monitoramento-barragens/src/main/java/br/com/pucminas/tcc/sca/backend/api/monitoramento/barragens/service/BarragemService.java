package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Barragem;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.BarragemRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.BarragemDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.BarragemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Barragem}.
 */
@Service
@Transactional
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class BarragemService {

    private final Logger log = LoggerFactory.getLogger(BarragemService.class);

    private final BarragemRepository barragemRepository;

    private final BarragemMapper barragemMapper;

    public BarragemService(BarragemRepository barragemRepository, BarragemMapper barragemMapper) {
        this.barragemRepository = barragemRepository;
        this.barragemMapper = barragemMapper;
    }

    /**
     * Save a barragem.
     *
     * @param barragemDTO the entity to save.
     * @return the persisted entity.
     */
    public BarragemDTO save(BarragemDTO barragemDTO) {
        log.debug("Request to save Barragem : {}", barragemDTO);
        Barragem barragem = barragemMapper.toEntity(barragemDTO);
        barragem = barragemRepository.save(barragem);
        return barragemMapper.toDto(barragem);
    }

    /**
     * Get all the barragems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BarragemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Barragems");
        return barragemRepository.findAll(pageable)
            .map(barragemMapper::toDto);
    }

    /**
     * Get one barragem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BarragemDTO> findOne(Long id) {
        log.debug("Request to get Barragem : {}", id);
        return barragemRepository.findById(id)
            .map(barragemMapper::toDto);
    }

    /**
     * Delete the barragem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Barragem : {}", id);
        barragemRepository.deleteById(id);
    }
}
