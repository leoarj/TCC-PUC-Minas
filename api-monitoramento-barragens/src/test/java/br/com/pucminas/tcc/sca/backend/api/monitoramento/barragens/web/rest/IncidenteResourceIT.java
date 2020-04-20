package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

//import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.RedisTestContainerExtension;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.ApiMonitoramentoBarragensApp;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Incidente;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Barragem;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.IncidenteRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.IncidenteService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.IncidenteMapper;

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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IncidenteResource} REST controller.
 */
@SpringBootTest(classes = ApiMonitoramentoBarragensApp.class)
//@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class IncidenteResourceIT {

    private static final UUID DEFAULT_IDENTIFICADOR = UUID.randomUUID();
    private static final UUID UPDATED_IDENTIFICADOR = UUID.randomUUID();

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CLASSIFICACAO = 1;
    private static final Integer UPDATED_CLASSIFICACAO = 2;

    @Autowired
    private IncidenteRepository incidenteRepository;

    @Autowired
    private IncidenteMapper incidenteMapper;

    @Autowired
    private IncidenteService incidenteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncidenteMockMvc;

    private Incidente incidente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incidente createEntity(EntityManager em) {
        Incidente incidente = new Incidente()
            .identificador(DEFAULT_IDENTIFICADOR)
            .data(DEFAULT_DATA)
            .classificacao(DEFAULT_CLASSIFICACAO);
        // Add required entity
        Barragem barragem;
        if (TestUtil.findAll(em, Barragem.class).isEmpty()) {
            barragem = BarragemResourceIT.createEntity(em);
            em.persist(barragem);
            em.flush();
        } else {
            barragem = TestUtil.findAll(em, Barragem.class).get(0);
        }
        incidente.setBarragem(barragem);
        return incidente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incidente createUpdatedEntity(EntityManager em) {
        Incidente incidente = new Incidente()
            .identificador(UPDATED_IDENTIFICADOR)
            .data(UPDATED_DATA)
            .classificacao(UPDATED_CLASSIFICACAO);
        // Add required entity
        Barragem barragem;
        if (TestUtil.findAll(em, Barragem.class).isEmpty()) {
            barragem = BarragemResourceIT.createUpdatedEntity(em);
            em.persist(barragem);
            em.flush();
        } else {
            barragem = TestUtil.findAll(em, Barragem.class).get(0);
        }
        incidente.setBarragem(barragem);
        return incidente;
    }

    @BeforeEach
    public void initTest() {
        incidente = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllIncidentes() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get all the incidenteList
        restIncidenteMockMvc.perform(get("/api/incidentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidente.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO)));
    }
    
    @Test
    @Transactional
    public void getIncidente() throws Exception {
        // Initialize the database
        incidenteRepository.saveAndFlush(incidente);

        // Get the incidente
        restIncidenteMockMvc.perform(get("/api/incidentes/{id}", incidente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incidente.getId().intValue()))
            .andExpect(jsonPath("$.identificador").value(DEFAULT_IDENTIFICADOR.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.classificacao").value(DEFAULT_CLASSIFICACAO));
    }

    @Test
    @Transactional
    public void getNonExistingIncidente() throws Exception {
        // Get the incidente
        restIncidenteMockMvc.perform(get("/api/incidentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
