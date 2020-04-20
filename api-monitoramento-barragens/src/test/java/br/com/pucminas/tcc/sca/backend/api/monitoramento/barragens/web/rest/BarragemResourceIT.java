package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

//import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.RedisTestContainerExtension;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.ApiMonitoramentoBarragensApp;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Barragem;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.BarragemRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.BarragemService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.BarragemDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.BarragemMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.GrauRisco;
/**
 * Integration tests for the {@link BarragemResource} REST controller.
 */
@SpringBootTest(classes = ApiMonitoramentoBarragensApp.class)
//@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class BarragemResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CAPACIDADE_METROS_CUBICOS = new BigDecimal(10);
    private static final BigDecimal UPDATED_CAPACIDADE_METROS_CUBICOS = new BigDecimal(11);

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final GrauRisco DEFAULT_GRAU_RISCO = GrauRisco.NENHUM;
    private static final GrauRisco UPDATED_GRAU_RISCO = GrauRisco.BAIXO;

    @Autowired
    private BarragemRepository barragemRepository;

    @Autowired
    private BarragemMapper barragemMapper;

    @Autowired
    private BarragemService barragemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBarragemMockMvc;

    private Barragem barragem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Barragem createEntity(EntityManager em) {
        Barragem barragem = new Barragem()
            .nome(DEFAULT_NOME)
            .capacidadeMetrosCubicos(DEFAULT_CAPACIDADE_METROS_CUBICOS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .grauRisco(DEFAULT_GRAU_RISCO);
        return barragem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Barragem createUpdatedEntity(EntityManager em) {
        Barragem barragem = new Barragem()
            .nome(UPDATED_NOME)
            .capacidadeMetrosCubicos(UPDATED_CAPACIDADE_METROS_CUBICOS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .grauRisco(UPDATED_GRAU_RISCO);
        return barragem;
    }

    @BeforeEach
    public void initTest() {
        barragem = createEntity(em);
    }

    @Test
    @Transactional
    public void createBarragem() throws Exception {
        int databaseSizeBeforeCreate = barragemRepository.findAll().size();

        // Create the Barragem
        BarragemDTO barragemDTO = barragemMapper.toDto(barragem);
        restBarragemMockMvc.perform(post("/api/barragens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barragemDTO)))
            .andExpect(status().isCreated());

        // Validate the Barragem in the database
        List<Barragem> barragemList = barragemRepository.findAll();
        assertThat(barragemList).hasSize(databaseSizeBeforeCreate + 1);
        Barragem testBarragem = barragemList.get(barragemList.size() - 1);
        assertThat(testBarragem.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testBarragem.getCapacidadeMetrosCubicos()).isEqualTo(DEFAULT_CAPACIDADE_METROS_CUBICOS);
        assertThat(testBarragem.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testBarragem.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testBarragem.getGrauRisco()).isEqualTo(DEFAULT_GRAU_RISCO);
    }

    @Test
    @Transactional
    public void createBarragemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = barragemRepository.findAll().size();

        // Create the Barragem with an existing ID
        barragem.setId(1L);
        BarragemDTO barragemDTO = barragemMapper.toDto(barragem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBarragemMockMvc.perform(post("/api/barragens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barragemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Barragem in the database
        List<Barragem> barragemList = barragemRepository.findAll();
        assertThat(barragemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = barragemRepository.findAll().size();
        // set the field null
        barragem.setNome(null);

        // Create the Barragem, which fails.
        BarragemDTO barragemDTO = barragemMapper.toDto(barragem);

        restBarragemMockMvc.perform(post("/api/barragens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barragemDTO)))
            .andExpect(status().isBadRequest());

        List<Barragem> barragemList = barragemRepository.findAll();
        assertThat(barragemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacidadeMetrosCubicosIsRequired() throws Exception {
        int databaseSizeBeforeTest = barragemRepository.findAll().size();
        // set the field null
        barragem.setCapacidadeMetrosCubicos(null);

        // Create the Barragem, which fails.
        BarragemDTO barragemDTO = barragemMapper.toDto(barragem);

        restBarragemMockMvc.perform(post("/api/barragens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barragemDTO)))
            .andExpect(status().isBadRequest());

        List<Barragem> barragemList = barragemRepository.findAll();
        assertThat(barragemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrauRiscoIsRequired() throws Exception {
        int databaseSizeBeforeTest = barragemRepository.findAll().size();
        // set the field null
        barragem.setGrauRisco(null);

        // Create the Barragem, which fails.
        BarragemDTO barragemDTO = barragemMapper.toDto(barragem);

        restBarragemMockMvc.perform(post("/api/barragens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barragemDTO)))
            .andExpect(status().isBadRequest());

        List<Barragem> barragemList = barragemRepository.findAll();
        assertThat(barragemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBarragems() throws Exception {
        // Initialize the database
        barragemRepository.saveAndFlush(barragem);

        // Get all the barragemList
        restBarragemMockMvc.perform(get("/api/barragens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barragem.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].capacidadeMetrosCubicos").value(hasItem(DEFAULT_CAPACIDADE_METROS_CUBICOS.intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].grauRisco").value(hasItem(DEFAULT_GRAU_RISCO.toString())));
    }
    
    @Test
    @Transactional
    public void getBarragem() throws Exception {
        // Initialize the database
        barragemRepository.saveAndFlush(barragem);

        // Get the barragem
        restBarragemMockMvc.perform(get("/api/barragens/{id}", barragem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(barragem.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.capacidadeMetrosCubicos").value(DEFAULT_CAPACIDADE_METROS_CUBICOS.intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.grauRisco").value(DEFAULT_GRAU_RISCO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBarragem() throws Exception {
        // Get the barragem
        restBarragemMockMvc.perform(get("/api/barragens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBarragem() throws Exception {
        // Initialize the database
        barragemRepository.saveAndFlush(barragem);

        int databaseSizeBeforeUpdate = barragemRepository.findAll().size();

        // Update the barragem
        Barragem updatedBarragem = barragemRepository.findById(barragem.getId()).get();
        // Disconnect from session so that the updates on updatedBarragem are not directly saved in db
        em.detach(updatedBarragem);
        updatedBarragem
            .nome(UPDATED_NOME)
            .capacidadeMetrosCubicos(UPDATED_CAPACIDADE_METROS_CUBICOS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .grauRisco(UPDATED_GRAU_RISCO);
        BarragemDTO barragemDTO = barragemMapper.toDto(updatedBarragem);

        restBarragemMockMvc.perform(put("/api/barragens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barragemDTO)))
            .andExpect(status().isOk());

        // Validate the Barragem in the database
        List<Barragem> barragemList = barragemRepository.findAll();
        assertThat(barragemList).hasSize(databaseSizeBeforeUpdate);
        Barragem testBarragem = barragemList.get(barragemList.size() - 1);
        assertThat(testBarragem.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testBarragem.getCapacidadeMetrosCubicos()).isEqualTo(UPDATED_CAPACIDADE_METROS_CUBICOS);
        assertThat(testBarragem.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testBarragem.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testBarragem.getGrauRisco()).isEqualTo(UPDATED_GRAU_RISCO);
    }

    @Test
    @Transactional
    public void updateNonExistingBarragem() throws Exception {
        int databaseSizeBeforeUpdate = barragemRepository.findAll().size();

        // Create the Barragem
        BarragemDTO barragemDTO = barragemMapper.toDto(barragem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarragemMockMvc.perform(put("/api/barragens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(barragemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Barragem in the database
        List<Barragem> barragemList = barragemRepository.findAll();
        assertThat(barragemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBarragem() throws Exception {
        // Initialize the database
        barragemRepository.saveAndFlush(barragem);

        int databaseSizeBeforeDelete = barragemRepository.findAll().size();

        // Delete the barragem
        restBarragemMockMvc.perform(delete("/api/barragens/{id}", barragem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Barragem> barragemList = barragemRepository.findAll();
        assertThat(barragemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
