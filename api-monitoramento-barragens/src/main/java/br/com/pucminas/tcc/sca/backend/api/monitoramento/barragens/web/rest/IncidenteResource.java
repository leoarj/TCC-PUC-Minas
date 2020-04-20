package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.IncidenteService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.errors.BadRequestAlertException;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteDTO;

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
 * REST controller for managing {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Incidente}.
 */
@RestController
@RequestMapping("/api")
public class IncidenteResource {

    private final Logger log = LoggerFactory.getLogger(IncidenteResource.class);

    private final IncidenteService incidenteService;

    public IncidenteResource(IncidenteService incidenteService) {
        this.incidenteService = incidenteService;
    }

    /**
     * {@code GET  /incidentes} : Retorna todos os Incidentes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incidentes in body.
     */
    @GetMapping("/incidentes")
    public ResponseEntity<List<IncidenteDTO>> getAllIncidentes(Pageable pageable) {
        log.debug("REST request to get a page of Incidentes");
        Page<IncidenteDTO> page = incidenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incidentes/:id} : Retorna um Incidente pelo ID.
     *
     * @param id the id of the incidenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incidenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incidentes/{id}")
    public ResponseEntity<IncidenteDTO> getIncidente(@PathVariable Long id) {
        log.debug("REST request to get Incidente : {}", id);
        Optional<IncidenteDTO> incidenteDTO = incidenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incidenteDTO);
    }
}
