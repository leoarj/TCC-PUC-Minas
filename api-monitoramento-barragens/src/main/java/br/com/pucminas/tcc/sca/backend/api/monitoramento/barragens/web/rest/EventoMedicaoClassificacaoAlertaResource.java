package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.EventoMedicaoClassificacaoAlertaService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.errors.BadRequestAlertException;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoClassificacaoAlertaDTO;

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
 * REST controller for managing {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicaoClassificacaoAlerta}.
 */
@RestController
@RequestMapping("/api")
public class EventoMedicaoClassificacaoAlertaResource {

    private final Logger log = LoggerFactory.getLogger(EventoMedicaoClassificacaoAlertaResource.class);

    private static final String ENTITY_NAME = "apiMonitoramentoBarragensEventoMedicaoClassificacaoAlerta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventoMedicaoClassificacaoAlertaService eventoMedicaoClassificacaoAlertaService;

    public EventoMedicaoClassificacaoAlertaResource(EventoMedicaoClassificacaoAlertaService eventoMedicaoClassificacaoAlertaService) {
        this.eventoMedicaoClassificacaoAlertaService = eventoMedicaoClassificacaoAlertaService;
    }

    /**
     * {@code POST  /evento-medicao-classificacao-alertas} : Cria uma nova Classificação de Alerta para Eventos de Medição.
     *
     * @param eventoMedicaoClassificacaoAlertaDTO the eventoMedicaoClassificacaoAlertaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventoMedicaoClassificacaoAlertaDTO, or with status {@code 400 (Bad Request)} if the eventoMedicaoClassificacaoAlerta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evento-medicao-classificacoes-alerta")
    public ResponseEntity<EventoMedicaoClassificacaoAlertaDTO> createEventoMedicaoClassificacaoAlerta(@Valid @RequestBody EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO) throws URISyntaxException {
        log.debug("REST request to save EventoMedicaoClassificacaoAlerta : {}", eventoMedicaoClassificacaoAlertaDTO);
        if (eventoMedicaoClassificacaoAlertaDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventoMedicaoClassificacaoAlerta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventoMedicaoClassificacaoAlertaDTO result = eventoMedicaoClassificacaoAlertaService.save(eventoMedicaoClassificacaoAlertaDTO);
        return ResponseEntity.created(new URI("/api/evento-medicao-classificacoes-alerta/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evento-medicao-classificacoes-alerta} : Atualiza uma nova Classificação de Alerta para Eventos de Medição existente.
     *
     * @param eventoMedicaoClassificacaoAlertaDTO the eventoMedicaoClassificacaoAlertaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventoMedicaoClassificacaoAlertaDTO,
     * or with status {@code 400 (Bad Request)} if the eventoMedicaoClassificacaoAlertaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventoMedicaoClassificacaoAlertaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evento-medicao-classificacoes-alerta")
    public ResponseEntity<EventoMedicaoClassificacaoAlertaDTO> updateEventoMedicaoClassificacaoAlerta(@Valid @RequestBody EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO) throws URISyntaxException {
        log.debug("REST request to update EventoMedicaoClassificacaoAlerta : {}", eventoMedicaoClassificacaoAlertaDTO);
        if (eventoMedicaoClassificacaoAlertaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventoMedicaoClassificacaoAlertaDTO result = eventoMedicaoClassificacaoAlertaService.save(eventoMedicaoClassificacaoAlertaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventoMedicaoClassificacaoAlertaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evento-medicao-classificacoes-alerta} : Retorna todas as Classificações de Alerta para Eventos de Medição.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventoMedicaoClassificacaoAlertas in body.
     */
    @GetMapping("/evento-medicao-classificacoes-alerta")
    public ResponseEntity<List<EventoMedicaoClassificacaoAlertaDTO>> getAllEventoMedicaoClassificacoesAlerta(Pageable pageable) {
        log.debug("REST request to get a page of EventoMedicaoClassificacaoAlertas");
        Page<EventoMedicaoClassificacaoAlertaDTO> page = eventoMedicaoClassificacaoAlertaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evento-medicao-classificacoes-alerta/:id} : Retorna uma Classificação de Alerta para Eventos de Medição pelo ID.
     *
     * @param id the id of the eventoMedicaoClassificacaoAlertaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventoMedicaoClassificacaoAlertaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evento-medicao-classificacoes-alerta/{id}")
    public ResponseEntity<EventoMedicaoClassificacaoAlertaDTO> getEventoMedicaoClassificacaoAlerta(@PathVariable Long id) {
        log.debug("REST request to get EventoMedicaoClassificacaoAlerta : {}", id);
        Optional<EventoMedicaoClassificacaoAlertaDTO> eventoMedicaoClassificacaoAlertaDTO = eventoMedicaoClassificacaoAlertaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventoMedicaoClassificacaoAlertaDTO);
    }

    /**
     * {@code DELETE  /evento-medicao-classificacoes-alerta/:id} : Deleta nova Classificação de Alerta para Eventos de Medição pelo ID.
     *
     * @param id the id of the eventoMedicaoClassificacaoAlertaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evento-medicao-classificacoes-alerta/{id}")
    public ResponseEntity<Void> deleteEventoMedicaoClassificacaoAlerta(@PathVariable Long id) {
        log.debug("REST request to delete EventoMedicaoClassificacaoAlerta : {}", id);
        eventoMedicaoClassificacaoAlertaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
