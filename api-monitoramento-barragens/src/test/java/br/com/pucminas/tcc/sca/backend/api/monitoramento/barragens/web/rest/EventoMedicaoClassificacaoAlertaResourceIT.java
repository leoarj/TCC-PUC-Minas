package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

//import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.RedisTestContainerExtension;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.ApiMonitoramentoBarragensApp;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicaoClassificacaoAlerta;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.EventoMedicaoClassificacaoAlertaRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.EventoMedicaoClassificacaoAlertaService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoClassificacaoAlertaDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.EventoMedicaoClassificacaoAlertaMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;
/**
 * Integration tests for the {@link EventoMedicaoClassificacaoAlertaResource} REST controller.
 */
@SpringBootTest(classes = ApiMonitoramentoBarragensApp.class)
//@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class EventoMedicaoClassificacaoAlertaResourceIT {

    private static final TipoMedicao DEFAULT_TIPO = TipoMedicao.NIVEL_ARMAZENAMENTO;
    private static final TipoMedicao UPDATED_TIPO = TipoMedicao.PRESSAO;

    private static final Integer DEFAULT_INTENSIDADE = 1;
    private static final Integer UPDATED_INTENSIDADE = 2;

    private static final Boolean DEFAULT_DISPARAR_ALERTAS = false;
    private static final Boolean UPDATED_DISPARAR_ALERTAS = true;

    @Autowired
    private EventoMedicaoClassificacaoAlertaRepository eventoMedicaoClassificacaoAlertaRepository;

    @Autowired
    private EventoMedicaoClassificacaoAlertaMapper eventoMedicaoClassificacaoAlertaMapper;

    @Autowired
    private EventoMedicaoClassificacaoAlertaService eventoMedicaoClassificacaoAlertaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventoMedicaoClassificacaoAlertaMockMvc;

    private EventoMedicaoClassificacaoAlerta eventoMedicaoClassificacaoAlerta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventoMedicaoClassificacaoAlerta createEntity(EntityManager em) {
        EventoMedicaoClassificacaoAlerta eventoMedicaoClassificacaoAlerta = new EventoMedicaoClassificacaoAlerta()
            .tipo(DEFAULT_TIPO)
            .intensidade(DEFAULT_INTENSIDADE)
            .dispararAlertas(DEFAULT_DISPARAR_ALERTAS);
        return eventoMedicaoClassificacaoAlerta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventoMedicaoClassificacaoAlerta createUpdatedEntity(EntityManager em) {
        EventoMedicaoClassificacaoAlerta eventoMedicaoClassificacaoAlerta = new EventoMedicaoClassificacaoAlerta()
            .tipo(UPDATED_TIPO)
            .intensidade(UPDATED_INTENSIDADE)
            .dispararAlertas(UPDATED_DISPARAR_ALERTAS);
        return eventoMedicaoClassificacaoAlerta;
    }

    @BeforeEach
    public void initTest() {
        eventoMedicaoClassificacaoAlerta = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventoMedicaoClassificacaoAlerta() throws Exception {
        int databaseSizeBeforeCreate = eventoMedicaoClassificacaoAlertaRepository.findAll().size();

        // Create the EventoMedicaoClassificacaoAlerta
        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO = eventoMedicaoClassificacaoAlertaMapper.toDto(eventoMedicaoClassificacaoAlerta);
        restEventoMedicaoClassificacaoAlertaMockMvc.perform(post("/api/evento-medicao-classificacoes-alerta")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventoMedicaoClassificacaoAlertaDTO)))
            .andExpect(status().isCreated());

        // Validate the EventoMedicaoClassificacaoAlerta in the database
        List<EventoMedicaoClassificacaoAlerta> eventoMedicaoClassificacaoAlertaList = eventoMedicaoClassificacaoAlertaRepository.findAll();
        assertThat(eventoMedicaoClassificacaoAlertaList).hasSize(databaseSizeBeforeCreate + 1);
        EventoMedicaoClassificacaoAlerta testEventoMedicaoClassificacaoAlerta = eventoMedicaoClassificacaoAlertaList.get(eventoMedicaoClassificacaoAlertaList.size() - 1);
        assertThat(testEventoMedicaoClassificacaoAlerta.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testEventoMedicaoClassificacaoAlerta.getIntensidade()).isEqualTo(DEFAULT_INTENSIDADE);
        assertThat(testEventoMedicaoClassificacaoAlerta.isDispararAlertas()).isEqualTo(DEFAULT_DISPARAR_ALERTAS);
    }

    @Test
    @Transactional
    public void createEventoMedicaoClassificacaoAlertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventoMedicaoClassificacaoAlertaRepository.findAll().size();

        // Create the EventoMedicaoClassificacaoAlerta with an existing ID
        eventoMedicaoClassificacaoAlerta.setId(1L);
        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO = eventoMedicaoClassificacaoAlertaMapper.toDto(eventoMedicaoClassificacaoAlerta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventoMedicaoClassificacaoAlertaMockMvc.perform(post("/api/evento-medicao-classificacoes-alerta")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventoMedicaoClassificacaoAlertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventoMedicaoClassificacaoAlerta in the database
        List<EventoMedicaoClassificacaoAlerta> eventoMedicaoClassificacaoAlertaList = eventoMedicaoClassificacaoAlertaRepository.findAll();
        assertThat(eventoMedicaoClassificacaoAlertaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoMedicaoClassificacaoAlertaRepository.findAll().size();
        // set the field null
        eventoMedicaoClassificacaoAlerta.setTipo(null);

        // Create the EventoMedicaoClassificacaoAlerta, which fails.
        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO = eventoMedicaoClassificacaoAlertaMapper.toDto(eventoMedicaoClassificacaoAlerta);

        restEventoMedicaoClassificacaoAlertaMockMvc.perform(post("/api/evento-medicao-classificacoes-alerta")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventoMedicaoClassificacaoAlertaDTO)))
            .andExpect(status().isBadRequest());

        List<EventoMedicaoClassificacaoAlerta> eventoMedicaoClassificacaoAlertaList = eventoMedicaoClassificacaoAlertaRepository.findAll();
        assertThat(eventoMedicaoClassificacaoAlertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntensidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoMedicaoClassificacaoAlertaRepository.findAll().size();
        // set the field null
        eventoMedicaoClassificacaoAlerta.setIntensidade(null);

        // Create the EventoMedicaoClassificacaoAlerta, which fails.
        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO = eventoMedicaoClassificacaoAlertaMapper.toDto(eventoMedicaoClassificacaoAlerta);

        restEventoMedicaoClassificacaoAlertaMockMvc.perform(post("/api/evento-medicao-classificacoes-alerta")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventoMedicaoClassificacaoAlertaDTO)))
            .andExpect(status().isBadRequest());

        List<EventoMedicaoClassificacaoAlerta> eventoMedicaoClassificacaoAlertaList = eventoMedicaoClassificacaoAlertaRepository.findAll();
        assertThat(eventoMedicaoClassificacaoAlertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDispararAlertasIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoMedicaoClassificacaoAlertaRepository.findAll().size();
        // set the field null
        eventoMedicaoClassificacaoAlerta.setDispararAlertas(null);

        // Create the EventoMedicaoClassificacaoAlerta, which fails.
        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO = eventoMedicaoClassificacaoAlertaMapper.toDto(eventoMedicaoClassificacaoAlerta);

        restEventoMedicaoClassificacaoAlertaMockMvc.perform(post("/api/evento-medicao-classificacoes-alerta")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventoMedicaoClassificacaoAlertaDTO)))
            .andExpect(status().isBadRequest());

        List<EventoMedicaoClassificacaoAlerta> eventoMedicaoClassificacaoAlertaList = eventoMedicaoClassificacaoAlertaRepository.findAll();
        assertThat(eventoMedicaoClassificacaoAlertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventoMedicaoClassificacaoAlertas() throws Exception {
        // Initialize the database
        eventoMedicaoClassificacaoAlertaRepository.saveAndFlush(eventoMedicaoClassificacaoAlerta);

        // Get all the eventoMedicaoClassificacaoAlertaList
        restEventoMedicaoClassificacaoAlertaMockMvc.perform(get("/api/evento-medicao-classificacoes-alerta?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventoMedicaoClassificacaoAlerta.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].intensidade").value(hasItem(DEFAULT_INTENSIDADE)))
            .andExpect(jsonPath("$.[*].dispararAlertas").value(hasItem(DEFAULT_DISPARAR_ALERTAS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEventoMedicaoClassificacaoAlerta() throws Exception {
        // Initialize the database
        eventoMedicaoClassificacaoAlertaRepository.saveAndFlush(eventoMedicaoClassificacaoAlerta);

        // Get the eventoMedicaoClassificacaoAlerta
        restEventoMedicaoClassificacaoAlertaMockMvc.perform(get("/api/evento-medicao-classificacoes-alerta/{id}", eventoMedicaoClassificacaoAlerta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventoMedicaoClassificacaoAlerta.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.intensidade").value(DEFAULT_INTENSIDADE))
            .andExpect(jsonPath("$.dispararAlertas").value(DEFAULT_DISPARAR_ALERTAS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEventoMedicaoClassificacaoAlerta() throws Exception {
        // Get the eventoMedicaoClassificacaoAlerta
        restEventoMedicaoClassificacaoAlertaMockMvc.perform(get("/api/evento-medicao-classificacoes-alerta/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventoMedicaoClassificacaoAlerta() throws Exception {
        // Initialize the database
        eventoMedicaoClassificacaoAlertaRepository.saveAndFlush(eventoMedicaoClassificacaoAlerta);

        int databaseSizeBeforeUpdate = eventoMedicaoClassificacaoAlertaRepository.findAll().size();

        // Update the eventoMedicaoClassificacaoAlerta
        EventoMedicaoClassificacaoAlerta updatedEventoMedicaoClassificacaoAlerta = eventoMedicaoClassificacaoAlertaRepository.findById(eventoMedicaoClassificacaoAlerta.getId()).get();
        // Disconnect from session so that the updates on updatedEventoMedicaoClassificacaoAlerta are not directly saved in db
        em.detach(updatedEventoMedicaoClassificacaoAlerta);
        updatedEventoMedicaoClassificacaoAlerta
            .tipo(UPDATED_TIPO)
            .intensidade(UPDATED_INTENSIDADE)
            .dispararAlertas(UPDATED_DISPARAR_ALERTAS);
        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO = eventoMedicaoClassificacaoAlertaMapper.toDto(updatedEventoMedicaoClassificacaoAlerta);

        restEventoMedicaoClassificacaoAlertaMockMvc.perform(put("/api/evento-medicao-classificacoes-alerta")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventoMedicaoClassificacaoAlertaDTO)))
            .andExpect(status().isOk());

        // Validate the EventoMedicaoClassificacaoAlerta in the database
        List<EventoMedicaoClassificacaoAlerta> eventoMedicaoClassificacaoAlertaList = eventoMedicaoClassificacaoAlertaRepository.findAll();
        assertThat(eventoMedicaoClassificacaoAlertaList).hasSize(databaseSizeBeforeUpdate);
        EventoMedicaoClassificacaoAlerta testEventoMedicaoClassificacaoAlerta = eventoMedicaoClassificacaoAlertaList.get(eventoMedicaoClassificacaoAlertaList.size() - 1);
        assertThat(testEventoMedicaoClassificacaoAlerta.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testEventoMedicaoClassificacaoAlerta.getIntensidade()).isEqualTo(UPDATED_INTENSIDADE);
        assertThat(testEventoMedicaoClassificacaoAlerta.isDispararAlertas()).isEqualTo(UPDATED_DISPARAR_ALERTAS);
    }

    @Test
    @Transactional
    public void updateNonExistingEventoMedicaoClassificacaoAlerta() throws Exception {
        int databaseSizeBeforeUpdate = eventoMedicaoClassificacaoAlertaRepository.findAll().size();

        // Create the EventoMedicaoClassificacaoAlerta
        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO = eventoMedicaoClassificacaoAlertaMapper.toDto(eventoMedicaoClassificacaoAlerta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoMedicaoClassificacaoAlertaMockMvc.perform(put("/api/evento-medicao-classificacoes-alerta")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventoMedicaoClassificacaoAlertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventoMedicaoClassificacaoAlerta in the database
        List<EventoMedicaoClassificacaoAlerta> eventoMedicaoClassificacaoAlertaList = eventoMedicaoClassificacaoAlertaRepository.findAll();
        assertThat(eventoMedicaoClassificacaoAlertaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventoMedicaoClassificacaoAlerta() throws Exception {
        // Initialize the database
        eventoMedicaoClassificacaoAlertaRepository.saveAndFlush(eventoMedicaoClassificacaoAlerta);

        int databaseSizeBeforeDelete = eventoMedicaoClassificacaoAlertaRepository.findAll().size();

        // Delete the eventoMedicaoClassificacaoAlerta
        restEventoMedicaoClassificacaoAlertaMockMvc.perform(delete("/api/evento-medicao-classificacoes-alerta/{id}", eventoMedicaoClassificacaoAlerta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventoMedicaoClassificacaoAlerta> eventoMedicaoClassificacaoAlertaList = eventoMedicaoClassificacaoAlertaRepository.findAll();
        assertThat(eventoMedicaoClassificacaoAlertaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
