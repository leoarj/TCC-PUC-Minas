package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

//import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.RedisTestContainerExtension;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.ApiMonitoramentoBarragensApp;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.IncidenteResultadoProcessamento;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.IncidenteResultadoProcessamentoRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.IncidenteResultadoProcessamentoService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteResultadoProcessamentoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.IncidenteResultadoProcessamentoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
//import java.util.List;
import java.util.UUID;

//import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IncidenteResultadoProcessamentoResource} REST controller.
 */
@SpringBootTest(classes = ApiMonitoramentoBarragensApp.class)
//@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class IncidenteResultadoProcessamentoResourceIT {

    private static final UUID DEFAULT_INCIDENTE_IDENTIFICADOR = UUID.randomUUID();
    private static final UUID UPDATED_INCIDENTE_IDENTIFICADOR = UUID.randomUUID();

    private static final Integer DEFAULT_INCIDENTE_CLASSIFICACAO = 1;
    private static final Integer UPDATED_INCIDENTE_CLASSIFICACAO = 2;

    private static final Boolean DEFAULT_SUCESSO = false;
    private static final Boolean UPDATED_SUCESSO = true;

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MENSAGEM = "AAAAAAAAAA";
    private static final String UPDATED_MENSAGEM = "BBBBBBBBBB";

    @Autowired
    private IncidenteResultadoProcessamentoRepository incidenteResultadoProcessamentoRepository;

    @Autowired
    private IncidenteResultadoProcessamentoMapper incidenteResultadoProcessamentoMapper;

    @Autowired
    private IncidenteResultadoProcessamentoService incidenteResultadoProcessamentoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncidenteResultadoProcessamentoMockMvc;

    private IncidenteResultadoProcessamento incidenteResultadoProcessamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncidenteResultadoProcessamento createEntity(EntityManager em) {
        IncidenteResultadoProcessamento incidenteResultadoProcessamento = new IncidenteResultadoProcessamento()
            .incidenteIdentificador(DEFAULT_INCIDENTE_IDENTIFICADOR)
            .incidenteClassificacao(DEFAULT_INCIDENTE_CLASSIFICACAO)
            .sucesso(DEFAULT_SUCESSO)
            .data(DEFAULT_DATA)
            .mensagem(DEFAULT_MENSAGEM);
        return incidenteResultadoProcessamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncidenteResultadoProcessamento createUpdatedEntity(EntityManager em) {
        IncidenteResultadoProcessamento incidenteResultadoProcessamento = new IncidenteResultadoProcessamento()
            .incidenteIdentificador(UPDATED_INCIDENTE_IDENTIFICADOR)
            .incidenteClassificacao(UPDATED_INCIDENTE_CLASSIFICACAO)
            .sucesso(UPDATED_SUCESSO)
            .data(UPDATED_DATA)
            .mensagem(UPDATED_MENSAGEM);
        return incidenteResultadoProcessamento;
    }

    @BeforeEach
    public void initTest() {
        incidenteResultadoProcessamento = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllIncidenteResultadoProcessamentos() throws Exception {
        // Initialize the database
        incidenteResultadoProcessamentoRepository.saveAndFlush(incidenteResultadoProcessamento);

        // Get all the incidenteResultadoProcessamentoList
        restIncidenteResultadoProcessamentoMockMvc.perform(get("/api/incidente-resultados-processamento?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidenteResultadoProcessamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].incidenteIdentificador").value(hasItem(DEFAULT_INCIDENTE_IDENTIFICADOR.toString())))
            .andExpect(jsonPath("$.[*].incidenteClassificacao").value(hasItem(DEFAULT_INCIDENTE_CLASSIFICACAO)))
            .andExpect(jsonPath("$.[*].sucesso").value(hasItem(DEFAULT_SUCESSO.booleanValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].mensagem").value(hasItem(DEFAULT_MENSAGEM)));
    }
    
    @Test
    @Transactional
    public void getIncidenteResultadoProcessamento() throws Exception {
        // Initialize the database
        incidenteResultadoProcessamentoRepository.saveAndFlush(incidenteResultadoProcessamento);

        // Get the incidenteResultadoProcessamento
        restIncidenteResultadoProcessamentoMockMvc.perform(get("/api/incidente-resultados-processamento/{id}", incidenteResultadoProcessamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incidenteResultadoProcessamento.getId().intValue()))
            .andExpect(jsonPath("$.incidenteIdentificador").value(DEFAULT_INCIDENTE_IDENTIFICADOR.toString()))
            .andExpect(jsonPath("$.incidenteClassificacao").value(DEFAULT_INCIDENTE_CLASSIFICACAO))
            .andExpect(jsonPath("$.sucesso").value(DEFAULT_SUCESSO.booleanValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.mensagem").value(DEFAULT_MENSAGEM));
    }

    @Test
    @Transactional
    public void getNonExistingIncidenteResultadoProcessamento() throws Exception {
        // Get the incidenteResultadoProcessamento
        restIncidenteResultadoProcessamentoMockMvc.perform(get("/api/incidente-resultados-processamento/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
