package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.EventoMedicaoService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external.EventoMedicaoResumoDTO;
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
 * REST controller for managing {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicao}.
 */
@RestController
@RequestMapping("/api")
public class EventoMedicaoResource {

    private final Logger log = LoggerFactory.getLogger(EventoMedicaoResource.class);
    
    private static final String ENTITY_NAME = "apiMonitoramentoBarragensEventoMedicao";
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventoMedicaoService eventoMedicaoService;

    public EventoMedicaoResource(EventoMedicaoService eventoMedicaoService) {
        this.eventoMedicaoService = eventoMedicaoService;
    }
    
    /**
     * {@code POST  /eventos-medicao} : Cria um novo Evento de Medição.
     *
     * @param barragemDTO the barragemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new barragemDTO, or with status {@code 400 (Bad Request)} if the barragem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eventos-medicao")
    public ResponseEntity<EventoMedicaoDTO> createEventoMedicao(@Valid @RequestBody EventoMedicaoResumoDTO eventoMedicaoResumoDTO) throws URISyntaxException {
        log.debug("REST request to save EventoMedicao : {}", eventoMedicaoResumoDTO);
        /**if (eventoMedicaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new evento medicao cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        
        EventoMedicaoDTO eventoMedicaoDTO = new EventoMedicaoDTO();
        eventoMedicaoDTO.setData(eventoMedicaoResumoDTO.getData());
        eventoMedicaoDTO.setIntensidade(eventoMedicaoResumoDTO.getIntensidade());
        eventoMedicaoDTO.setTipo(eventoMedicaoResumoDTO.getTipo());
        eventoMedicaoDTO.setSensorIdentificador(eventoMedicaoResumoDTO.getSensorIdentificador());
        
        EventoMedicaoDTO result = eventoMedicaoService.save(eventoMedicaoDTO);
        return ResponseEntity.created(new URI("/api/eventos-medicao/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eventos-medicao} : Retorna todos os Eventos de Medição.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventoMedicaos in body.
     */
    @GetMapping("/eventos-medicao")
    public ResponseEntity<List<EventoMedicaoDTO>> getAllEventosMedicao(Pageable pageable) {
        log.debug("REST request to get a page of EventoMedicaos");
        Page<EventoMedicaoDTO> page = eventoMedicaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eventos-medicao/:id} : Retorna um Eventos de Medição pelo ID.
     *
     * @param id the id of the eventoMedicaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventoMedicaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eventos-medicao/{id}")
    public ResponseEntity<EventoMedicaoDTO> getEventoMedicao(@PathVariable Long id) {
        log.debug("REST request to get EventoMedicao : {}", id);
        Optional<EventoMedicaoDTO> eventoMedicaoDTO = eventoMedicaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventoMedicaoDTO);
    }
}
