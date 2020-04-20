package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.BarragemService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.errors.BadRequestAlertException;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.BarragemDTO;

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
 * REST controller for managing {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Barragem}.
 */
@RestController
@RequestMapping("/api")
public class BarragemResource {

    private final Logger log = LoggerFactory.getLogger(BarragemResource.class);

    private static final String ENTITY_NAME = "apiMonitoramentoBarragensBarragem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BarragemService barragemService;

    public BarragemResource(BarragemService barragemService) {
        this.barragemService = barragemService;
    }

    /**
     * {@code POST  /barragens} : Cria uma nova barragem.
     *
     * @param barragemDTO the barragemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new barragemDTO, or with status {@code 400 (Bad Request)} if the barragem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/barragens")
    public ResponseEntity<BarragemDTO> createBarragem(@Valid @RequestBody BarragemDTO barragemDTO) throws URISyntaxException {
        log.debug("REST request to save Barragem : {}", barragemDTO);
        if (barragemDTO.getId() != null) {
            throw new BadRequestAlertException("Uma nova barragem foi criada com o ID", ENTITY_NAME, "idexists");
        }
        BarragemDTO result = barragemService.save(barragemDTO);
        return ResponseEntity.created(new URI("/api/barragens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /barragens} : Atualiza uma barragem existente.
     *
     * @param barragemDTO the barragemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated barragemDTO,
     * or with status {@code 400 (Bad Request)} if the barragemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the barragemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/barragens")
    public ResponseEntity<BarragemDTO> updateBarragem(@Valid @RequestBody BarragemDTO barragemDTO) throws URISyntaxException {
        log.debug("REST request to update Barragem : {}", barragemDTO);
        if (barragemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BarragemDTO result = barragemService.save(barragemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, barragemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /barragens} : Retorna todas as barragens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of barragems in body.
     */
    @GetMapping("/barragens")
    public ResponseEntity<List<BarragemDTO>> getAllBarragens(Pageable pageable) {
        log.debug("REST request to get a page of Barragems");
        Page<BarragemDTO> page = barragemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /barragens/:id} : Retorna uma barragem pelo ID.
     *
     * @param id the id of the barragemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the barragemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/barragens/{id}")
    public ResponseEntity<BarragemDTO> getBarragem(@PathVariable Long id) {
        log.debug("REST request to get Barragem : {}", id);
        Optional<BarragemDTO> barragemDTO = barragemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(barragemDTO);
    }

    /**
     * {@code DELETE  /barragens/:id} : Deleta uma barragem pelo ID.
     *
     * @param id the id of the barragemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/barragens/{id}")
    public ResponseEntity<Void> deleteBarragem(@PathVariable Long id) {
        log.debug("REST request to delete Barragem : {}", id);
        barragemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
