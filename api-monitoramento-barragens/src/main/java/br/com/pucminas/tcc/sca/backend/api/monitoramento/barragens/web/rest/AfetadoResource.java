package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.AfetadoService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.errors.BadRequestAlertException;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.AfetadoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Afetado}.
 */
@RestController
@RequestMapping("/api")
public class AfetadoResource {

    private final Logger log = LoggerFactory.getLogger(AfetadoResource.class);

    private static final String ENTITY_NAME = "apiMonitoramentoBarragensAfetado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AfetadoService afetadoService;

    public AfetadoResource(AfetadoService afetadoService) {
        this.afetadoService = afetadoService;
    }

    /**
     * {@code POST  /afetados} : Cria um novo afetado.
     *
     * @param afetadoDTO the afetadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new afetadoDTO, or with status {@code 400 (Bad Request)} if the afetado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/afetados")
    public ResponseEntity<AfetadoDTO> createAfetado(@Valid @RequestBody AfetadoDTO afetadoDTO) throws URISyntaxException {
        log.debug("REST request to save Afetado : {}", afetadoDTO);
        if (afetadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new afetado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AfetadoDTO result = afetadoService.save(afetadoDTO);
        return ResponseEntity.created(new URI("/api/afetados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /afetados} : Atualiza um afetado existente.
     *
     * @param afetadoDTO the afetadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afetadoDTO,
     * or with status {@code 400 (Bad Request)} if the afetadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the afetadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/afetados")
    public ResponseEntity<AfetadoDTO> updateAfetado(@Valid @RequestBody AfetadoDTO afetadoDTO) throws URISyntaxException {
        log.debug("REST request to update Afetado : {}", afetadoDTO);
        if (afetadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AfetadoDTO result = afetadoService.save(afetadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, afetadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /afetados} : Retorna todos os afetados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of afetados in body.
     */
    @GetMapping("/afetados")
    public ResponseEntity<List<AfetadoDTO>> getAllAfetados(Pageable pageable) {
        log.debug("REST request to get a page of Afetados");
        Page<AfetadoDTO> page = afetadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /afetados/:id} : Retorna um afetado pelo ID.
     *
     * @param id the id of the afetadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the afetadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/afetados/{id}")
    public ResponseEntity<AfetadoDTO> getAfetado(@PathVariable Long id) {
        log.debug("REST request to get Afetado : {}", id);
        Optional<AfetadoDTO> afetadoDTO = afetadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(afetadoDTO);
    }

    /**
     * {@code DELETE  /afetados/:id} : Deleta um afetado pelo ID.
     *
     * @param id the id of the afetadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/afetados/{id}")
    public ResponseEntity<Void> deleteAfetado(@PathVariable Long id) {
        log.debug("REST request to delete Afetado : {}", id);
        afetadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
