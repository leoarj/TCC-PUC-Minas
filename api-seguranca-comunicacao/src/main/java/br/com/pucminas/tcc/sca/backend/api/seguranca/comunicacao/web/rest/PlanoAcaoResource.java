package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.web.rest;

import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.PlanoAcaoService;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.web.rest.errors.BadRequestAlertException;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.PlanoAcaoDTO;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.external.DadosExecucaoPlanoAcaoDTO;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.exceptions.PlanoAcaoNotFoundException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.PlanoAcao}.
 */
@RestController
@RequestMapping("/api")
public class PlanoAcaoResource {

    private final Logger log = LoggerFactory.getLogger(PlanoAcaoResource.class);

    private static final String ENTITY_NAME = "apiSegurancaComunicacaoPlanoAcao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoAcaoService planoAcaoService;
    
    public PlanoAcaoResource(PlanoAcaoService planoAcaoService) {
        this.planoAcaoService = planoAcaoService;
    }

    /**
     * {@code POST  /planos-acao} : Create a new planoAcao.
     *
     * @param planoAcaoDTO the planoAcaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoAcaoDTO, or with status {@code 400 (Bad Request)} if the planoAcao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/planos-acao")
    public ResponseEntity<PlanoAcaoDTO> createPlanoAcao(@Valid @RequestBody PlanoAcaoDTO planoAcaoDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoAcao : {}", planoAcaoDTO);
        if (planoAcaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoAcao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoAcaoDTO result = planoAcaoService.save(planoAcaoDTO);
        return ResponseEntity.created(new URI("/api/planos-acao/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /planos-acao} : Updates an existing planoAcao.
     *
     * @param planoAcaoDTO the planoAcaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoAcaoDTO,
     * or with status {@code 400 (Bad Request)} if the planoAcaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoAcaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/planos-acao")
    public ResponseEntity<PlanoAcaoDTO> updatePlanoAcao(@Valid @RequestBody PlanoAcaoDTO planoAcaoDTO) throws URISyntaxException {
        log.debug("REST request to update PlanoAcao : {}", planoAcaoDTO);
        if (planoAcaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoAcaoDTO result = planoAcaoService.save(planoAcaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoAcaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /planos-acao} : get all the planoAcaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoAcaos in body.
     */
    @GetMapping("/planos-acao")
    public ResponseEntity<List<PlanoAcaoDTO>> getAllPlanosAcao(Pageable pageable) {
        log.debug("REST request to get a page of PlanoAcaos");
        Page<PlanoAcaoDTO> page = planoAcaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /planos-acao/:id} : get the "id" planoAcao.
     *
     * @param id the id of the planoAcaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoAcaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/planos-acao/{id}")
    public ResponseEntity<PlanoAcaoDTO> getPlanoAcao(@PathVariable Long id) {
        log.debug("REST request to get PlanoAcao : {}", id);
        Optional<PlanoAcaoDTO> planoAcaoDTO = planoAcaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoAcaoDTO);
    }

    /**
     * {@code DELETE  /planos-acao/:id} : delete the "id" planoAcao.
     *
     * @param id the id of the planoAcaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/planos-acao/{id}")
    public ResponseEntity<Void> deletePlanoAcao(@PathVariable Long id) {
        log.debug("REST request to delete PlanoAcao : {}", id);
        planoAcaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code POST  /planos-acao} : Create a new planoAcao.
     *
     * @param planoAcaoDTO the planoAcaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoAcaoDTO, or with status {@code 400 (Bad Request)} if the planoAcao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/planos-acao/executar")
    public ResponseEntity<String> executarPlanoAcao(@Valid @RequestBody DadosExecucaoPlanoAcaoDTO dadosExecucaoPlanoAcaoDTO) throws URISyntaxException {
    	log.debug("REST request to execute PlanoAcao : {}", dadosExecucaoPlanoAcaoDTO);
                
        if (dadosExecucaoPlanoAcaoDTO.getClassificacao() == 0) {
        	throw new BadRequestAlertException("Plano de Ação não pode ter classificação zero", ENTITY_NAME, "zeroclassificacao");
        }        
        if (dadosExecucaoPlanoAcaoDTO.getBarragem() == null) {
        	throw new BadRequestAlertException("Requisição para executar Plano de Ação sem Barragem informada", ENTITY_NAME, "barragemnaoinformada");
        }        
        if (dadosExecucaoPlanoAcaoDTO.getBarragem().getAfetados().isEmpty()) {
        	throw new BadRequestAlertException("Requisição para executar Plano de Ação sem Afetados", ENTITY_NAME, "semafetados");
        }
        
        try {
        	planoAcaoService.executarPlanoAcao(dadosExecucaoPlanoAcaoDTO);
        	
        	return ResponseEntity
        			.accepted() // HTTP 202
        			.body("Solicitação aceita.\nEnviando alertas aos afetados.");
        } catch (PlanoAcaoNotFoundException e) {
        	return ResponseEntity
        			.status(HttpStatus.NOT_ACCEPTABLE) // HTTP 406
        			.body("Solicitação não aceitável.\nErro: " + e.getMessage());
        } 
        catch (Exception e) {
        	return ResponseEntity
        			.status(HttpStatus.INTERNAL_SERVER_ERROR) // HTTP 500
        			.body("Houve um erro ao executar a solicitação.\nErro: " + e.getMessage());
        } 
    }
}
