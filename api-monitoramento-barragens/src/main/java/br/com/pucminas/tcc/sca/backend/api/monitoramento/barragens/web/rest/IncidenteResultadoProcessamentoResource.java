package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.IncidenteResultadoProcessamentoService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.errors.BadRequestAlertException;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteResultadoProcessamentoDTO;

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
 * REST controller for managing {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.IncidenteResultadoProcessamento}.
 */
@RestController
@RequestMapping("/api")
public class IncidenteResultadoProcessamentoResource {

    private final Logger log = LoggerFactory.getLogger(IncidenteResultadoProcessamentoResource.class);

    private static final String ENTITY_NAME = "apiMonitoramentoBarragensIncidenteResultadoProcessamento";
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private final IncidenteResultadoProcessamentoService incidenteResultadoProcessamentoService;

    public IncidenteResultadoProcessamentoResource(IncidenteResultadoProcessamentoService incidenteResultadoProcessamentoService) {
        this.incidenteResultadoProcessamentoService = incidenteResultadoProcessamentoService;
    }

    /**
     * {@code POST  /incidente-resultados-processamento} : Cria um novo Resultado de Processamento para um Incidente.
     *
     * @param barragemDTO the barragemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new barragemDTO, or with status {@code 400 (Bad Request)} if the barragem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incidente-resultados-processamento")
    public ResponseEntity<IncidenteResultadoProcessamentoDTO> createIncidenteResultadoProcessamento(@Valid @RequestBody IncidenteResultadoProcessamentoDTO incidenteResultadoProcessamentoDTO) throws URISyntaxException {
        log.debug("REST request to save IncidenteResultadoProcessamento : {}", incidenteResultadoProcessamentoDTO);
        if (incidenteResultadoProcessamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new incidente resultado processamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidenteResultadoProcessamentoDTO result = incidenteResultadoProcessamentoService.save(incidenteResultadoProcessamentoDTO);
        return ResponseEntity.created(new URI("/api/incidente-resultados-processamento/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code GET  /incidente-resultados-processamento} : Retorna todos os Resultados de Processamento para Incidentes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incidenteResultadoProcessamentos in body.
     */
    @GetMapping("/incidente-resultados-processamento")
    public ResponseEntity<List<IncidenteResultadoProcessamentoDTO>> getAllIncidenteResultadosProcessamento(Pageable pageable) {
        log.debug("REST request to get a page of IncidenteResultadoProcessamentos");
        Page<IncidenteResultadoProcessamentoDTO> page = incidenteResultadoProcessamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incidente-resultados-processamento/:id} : Retorna um Resultado de Processamento para Incidentes pelo ID.
     *
     * @param id the id of the incidenteResultadoProcessamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incidenteResultadoProcessamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incidente-resultados-processamento/{id}")
    public ResponseEntity<IncidenteResultadoProcessamentoDTO> getIncidenteResultadoProcessamento(@PathVariable Long id) {
        log.debug("REST request to get IncidenteResultadoProcessamento : {}", id);
        Optional<IncidenteResultadoProcessamentoDTO> incidenteResultadoProcessamentoDTO = incidenteResultadoProcessamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incidenteResultadoProcessamentoDTO);
    }
}
