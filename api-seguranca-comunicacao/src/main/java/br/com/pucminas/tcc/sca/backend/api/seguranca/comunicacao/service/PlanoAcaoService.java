package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service;

import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.PlanoAcao;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.repository.PlanoAcaoRepository;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.async.ExecutarPlanoAcaoServiceAsync;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.PlanoAcaoDTO;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.external.DadosExecucaoPlanoAcaoDTO;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.exceptions.ExecutarPlanoAcaoException;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.exceptions.PlanoAcaoNotFoundException;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.mapper.PlanoAcaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PlanoAcao}.
 */
@Service
@Transactional
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class PlanoAcaoService {

    private final Logger log = LoggerFactory.getLogger(PlanoAcaoService.class);

    private final PlanoAcaoRepository planoAcaoRepository;

    private final PlanoAcaoMapper planoAcaoMapper;
    
    private final ExecutarPlanoAcaoServiceAsync executarPlanoAcaoServiceAsync;

    public PlanoAcaoService(PlanoAcaoRepository planoAcaoRepository, PlanoAcaoMapper planoAcaoMapper, ExecutarPlanoAcaoServiceAsync executarPlanoAcaoServiceAsync) {
        this.planoAcaoRepository = planoAcaoRepository;
        this.planoAcaoMapper = planoAcaoMapper;
        this.executarPlanoAcaoServiceAsync = executarPlanoAcaoServiceAsync;
    }

    /**
     * Save a planoAcao.
     *
     * @param planoAcaoDTO the entity to save.
     * @return the persisted entity.
     */
    public PlanoAcaoDTO save(PlanoAcaoDTO planoAcaoDTO) {
        log.debug("Request to save PlanoAcao : {}", planoAcaoDTO);
        PlanoAcao planoAcao = planoAcaoMapper.toEntity(planoAcaoDTO);
        planoAcao = planoAcaoRepository.save(planoAcao);
        return planoAcaoMapper.toDto(planoAcao);
    }

    /**
     * Get all the planoAcaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoAcaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoAcaos");
        return planoAcaoRepository.findAll(pageable)
            .map(planoAcaoMapper::toDto);
    }

    /**
     * Get one planoAcao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoAcaoDTO> findOne(Long id) {
        log.debug("Request to get PlanoAcao : {}", id);
        return planoAcaoRepository.findById(id)
            .map(planoAcaoMapper::toDto);
    }

    /**
     * Delete the planoAcao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanoAcao : {}", id);
        planoAcaoRepository.deleteById(id);
    }
    
    /**
     * Get one planoAcao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoAcaoDTO> findByClassificacao(Integer classificacao) {
        log.debug("Request to get PlanoAcao : {}", classificacao);
        return planoAcaoRepository.findByClassificacao(classificacao)
            .map(planoAcaoMapper::toDto);
    }
    
	public void executarPlanoAcao(DadosExecucaoPlanoAcaoDTO dadosExecucaoPlanoAcaoDTO) throws ExecutarPlanoAcaoException, PlanoAcaoNotFoundException {
		try {
			Optional<PlanoAcaoDTO> optPlanoAcao = null;
			optPlanoAcao = this.findByClassificacao(dadosExecucaoPlanoAcaoDTO.getClassificacao());
			
			if (optPlanoAcao.isPresent()) {				
				executarPlanoAcaoServiceAsync.executeAsync(optPlanoAcao.get(), dadosExecucaoPlanoAcaoDTO);								
			} else {
				throw new PlanoAcaoNotFoundException(
						String.format("Plano de ação com a classificação: %d não encontrado!", dadosExecucaoPlanoAcaoDTO.getClassificacao()));
			}
		} catch (PlanoAcaoNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ExecutarPlanoAcaoException(
					String.format("Erro ao executar o Plano de ação com a classificação: %d.", dadosExecucaoPlanoAcaoDTO.getClassificacao()), e);
		}
	}
}
