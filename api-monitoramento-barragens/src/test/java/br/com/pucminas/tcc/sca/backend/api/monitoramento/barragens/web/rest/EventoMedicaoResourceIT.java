package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

//import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.RedisTestContainerExtension;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.ApiMonitoramentoBarragensApp;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicao;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.EventoMedicaoRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.EventoMedicaoService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.EventoMedicaoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;
/**
 * Integration tests for the {@link EventoMedicaoResource} REST controller.
 */
@SpringBootTest(classes = ApiMonitoramentoBarragensApp.class)
//@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class EventoMedicaoResourceIT {

    private static final UUID DEFAULT_IDENTIFICADOR = UUID.randomUUID();
    private static final UUID UPDATED_IDENTIFICADOR = UUID.randomUUID();

    private static final UUID DEFAULT_SENSOR_IDENTIFICADOR = UUID.randomUUID();
    private static final UUID UPDATED_SENSOR_IDENTIFICADOR = UUID.randomUUID();

    private static final TipoMedicao DEFAULT_TIPO = TipoMedicao.NIVEL_ARMAZENAMENTO;
    private static final TipoMedicao UPDATED_TIPO = TipoMedicao.PRESSAO;

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_INTENSIDADE = 1;
    private static final Integer UPDATED_INTENSIDADE = 2;

    @Autowired
    private EventoMedicaoRepository eventoMedicaoRepository;

    @Autowired
    private EventoMedicaoMapper eventoMedicaoMapper;

    @Autowired
    private EventoMedicaoService eventoMedicaoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventoMedicaoMockMvc;

    private EventoMedicao eventoMedicao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventoMedicao createEntity(EntityManager em) {
        EventoMedicao eventoMedicao = new EventoMedicao()
            .identificador(DEFAULT_IDENTIFICADOR)
            .sensorIdentificador(DEFAULT_SENSOR_IDENTIFICADOR)
            .tipo(DEFAULT_TIPO)
            .data(DEFAULT_DATA)
            .intensidade(DEFAULT_INTENSIDADE);
        return eventoMedicao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventoMedicao createUpdatedEntity(EntityManager em) {
        EventoMedicao eventoMedicao = new EventoMedicao()
            .identificador(UPDATED_IDENTIFICADOR)
            .sensorIdentificador(UPDATED_SENSOR_IDENTIFICADOR)
            .tipo(UPDATED_TIPO)
            .data(UPDATED_DATA)
            .intensidade(UPDATED_INTENSIDADE);
        return eventoMedicao;
    }

    @BeforeEach
    public void initTest() {
        eventoMedicao = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllEventoMedicaos() throws Exception {
        // Initialize the database
        eventoMedicaoRepository.saveAndFlush(eventoMedicao);

        // Get all the eventoMedicaoList
        restEventoMedicaoMockMvc.perform(get("/api/eventos-medicao?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventoMedicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR.toString())))
            .andExpect(jsonPath("$.[*].sensorIdentificador").value(hasItem(DEFAULT_SENSOR_IDENTIFICADOR.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].intensidade").value(hasItem(DEFAULT_INTENSIDADE)));
    }
    
    @Test
    @Transactional
    public void getEventoMedicao() throws Exception {
        // Initialize the database
        eventoMedicaoRepository.saveAndFlush(eventoMedicao);

        // Get the eventoMedicao
        restEventoMedicaoMockMvc.perform(get("/api/eventos-medicao/{id}", eventoMedicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventoMedicao.getId().intValue()))
            .andExpect(jsonPath("$.identificador").value(DEFAULT_IDENTIFICADOR.toString()))
            .andExpect(jsonPath("$.sensorIdentificador").value(DEFAULT_SENSOR_IDENTIFICADOR.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.intensidade").value(DEFAULT_INTENSIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingEventoMedicao() throws Exception {
        // Get the eventoMedicao
        restEventoMedicaoMockMvc.perform(get("/api/eventos-medicao/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
