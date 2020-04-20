package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.web.rest;

//import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.RedisTestContainerExtension;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.ApiSegurancaComunicacaoApp;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.PlanoAcao;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.repository.PlanoAcaoRepository;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.PlanoAcaoService;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.PlanoAcaoDTO;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.mapper.PlanoAcaoMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.enumeration.TipoPlanoAcao;
/**
 * Integration tests for the {@link PlanoAcaoResource} REST controller.
 */
@SpringBootTest(classes = ApiSegurancaComunicacaoApp.class)
//@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class PlanoAcaoResourceIT {

    private static final TipoPlanoAcao DEFAULT_TIPO = TipoPlanoAcao.SMS;
    private static final TipoPlanoAcao UPDATED_TIPO = TipoPlanoAcao.EMAIL;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLASSIFICACAO = 1;
    private static final Integer UPDATED_CLASSIFICACAO = 2;

    private static final String DEFAULT_MENSAGEM_ALTERTA = "AAAAAAAAAA";
    private static final String UPDATED_MENSAGEM_ALTERTA = "BBBBBBBBBB";

    @Autowired
    private PlanoAcaoRepository planoAcaoRepository;

    @Autowired
    private PlanoAcaoMapper planoAcaoMapper;

    @Autowired
    private PlanoAcaoService planoAcaoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoAcaoMockMvc;

    private PlanoAcao planoAcao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoAcao createEntity(EntityManager em) {
        PlanoAcao planoAcao = new PlanoAcao()
            .tipo(DEFAULT_TIPO)
            .descricao(DEFAULT_DESCRICAO)
            .classificacao(DEFAULT_CLASSIFICACAO)
            .mensagemAlterta(DEFAULT_MENSAGEM_ALTERTA);
        return planoAcao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoAcao createUpdatedEntity(EntityManager em) {
        PlanoAcao planoAcao = new PlanoAcao()
            .tipo(UPDATED_TIPO)
            .descricao(UPDATED_DESCRICAO)
            .classificacao(UPDATED_CLASSIFICACAO)
            .mensagemAlterta(UPDATED_MENSAGEM_ALTERTA);
        return planoAcao;
    }

    @BeforeEach
    public void initTest() {
        planoAcao = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoAcao() throws Exception {
        int databaseSizeBeforeCreate = planoAcaoRepository.findAll().size();

        // Create the PlanoAcao
        PlanoAcaoDTO planoAcaoDTO = planoAcaoMapper.toDto(planoAcao);
        restPlanoAcaoMockMvc.perform(post("/api/planos-acao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoAcaoDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanoAcao in the database
        List<PlanoAcao> planoAcaoList = planoAcaoRepository.findAll();
        assertThat(planoAcaoList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoAcao testPlanoAcao = planoAcaoList.get(planoAcaoList.size() - 1);
        assertThat(testPlanoAcao.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testPlanoAcao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPlanoAcao.getClassificacao()).isEqualTo(DEFAULT_CLASSIFICACAO);
        assertThat(testPlanoAcao.getMensagemAlterta()).isEqualTo(DEFAULT_MENSAGEM_ALTERTA);
    }

    @Test
    @Transactional
    public void createPlanoAcaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoAcaoRepository.findAll().size();

        // Create the PlanoAcao with an existing ID
        planoAcao.setId(1L);
        PlanoAcaoDTO planoAcaoDTO = planoAcaoMapper.toDto(planoAcao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoAcaoMockMvc.perform(post("/api/planos-acao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoAcaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoAcao in the database
        List<PlanoAcao> planoAcaoList = planoAcaoRepository.findAll();
        assertThat(planoAcaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoAcaoRepository.findAll().size();
        // set the field null
        planoAcao.setDescricao(null);

        // Create the PlanoAcao, which fails.
        PlanoAcaoDTO planoAcaoDTO = planoAcaoMapper.toDto(planoAcao);

        restPlanoAcaoMockMvc.perform(post("/api/planos-acao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoAcaoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoAcao> planoAcaoList = planoAcaoRepository.findAll();
        assertThat(planoAcaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClassificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoAcaoRepository.findAll().size();
        // set the field null
        planoAcao.setClassificacao(null);

        // Create the PlanoAcao, which fails.
        PlanoAcaoDTO planoAcaoDTO = planoAcaoMapper.toDto(planoAcao);

        restPlanoAcaoMockMvc.perform(post("/api/planos-acao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoAcaoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoAcao> planoAcaoList = planoAcaoRepository.findAll();
        assertThat(planoAcaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMensagemAltertaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoAcaoRepository.findAll().size();
        // set the field null
        planoAcao.setMensagemAlterta(null);

        // Create the PlanoAcao, which fails.
        PlanoAcaoDTO planoAcaoDTO = planoAcaoMapper.toDto(planoAcao);

        restPlanoAcaoMockMvc.perform(post("/api/planos-acao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoAcaoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanoAcao> planoAcaoList = planoAcaoRepository.findAll();
        assertThat(planoAcaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanoAcaos() throws Exception {
        // Initialize the database
        planoAcaoRepository.saveAndFlush(planoAcao);

        // Get all the planoAcaoList
        restPlanoAcaoMockMvc.perform(get("/api/planos-acao?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoAcao.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO)))
            .andExpect(jsonPath("$.[*].mensagemAlterta").value(hasItem(DEFAULT_MENSAGEM_ALTERTA)));
    }
    
    @Test
    @Transactional
    public void getPlanoAcao() throws Exception {
        // Initialize the database
        planoAcaoRepository.saveAndFlush(planoAcao);

        // Get the planoAcao
        restPlanoAcaoMockMvc.perform(get("/api/planos-acao/{id}", planoAcao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoAcao.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.classificacao").value(DEFAULT_CLASSIFICACAO))
            .andExpect(jsonPath("$.mensagemAlterta").value(DEFAULT_MENSAGEM_ALTERTA));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoAcao() throws Exception {
        // Get the planoAcao
        restPlanoAcaoMockMvc.perform(get("/api/planos-acao/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoAcao() throws Exception {
        // Initialize the database
        planoAcaoRepository.saveAndFlush(planoAcao);

        int databaseSizeBeforeUpdate = planoAcaoRepository.findAll().size();

        // Update the planoAcao
        PlanoAcao updatedPlanoAcao = planoAcaoRepository.findById(planoAcao.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoAcao are not directly saved in db
        em.detach(updatedPlanoAcao);
        updatedPlanoAcao
            .tipo(UPDATED_TIPO)
            .descricao(UPDATED_DESCRICAO)
            .classificacao(UPDATED_CLASSIFICACAO)
            .mensagemAlterta(UPDATED_MENSAGEM_ALTERTA);
        PlanoAcaoDTO planoAcaoDTO = planoAcaoMapper.toDto(updatedPlanoAcao);

        restPlanoAcaoMockMvc.perform(put("/api/planos-acao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoAcaoDTO)))
            .andExpect(status().isOk());

        // Validate the PlanoAcao in the database
        List<PlanoAcao> planoAcaoList = planoAcaoRepository.findAll();
        assertThat(planoAcaoList).hasSize(databaseSizeBeforeUpdate);
        PlanoAcao testPlanoAcao = planoAcaoList.get(planoAcaoList.size() - 1);
        assertThat(testPlanoAcao.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testPlanoAcao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPlanoAcao.getClassificacao()).isEqualTo(UPDATED_CLASSIFICACAO);
        assertThat(testPlanoAcao.getMensagemAlterta()).isEqualTo(UPDATED_MENSAGEM_ALTERTA);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoAcao() throws Exception {
        int databaseSizeBeforeUpdate = planoAcaoRepository.findAll().size();

        // Create the PlanoAcao
        PlanoAcaoDTO planoAcaoDTO = planoAcaoMapper.toDto(planoAcao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoAcaoMockMvc.perform(put("/api/planos-acao")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planoAcaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoAcao in the database
        List<PlanoAcao> planoAcaoList = planoAcaoRepository.findAll();
        assertThat(planoAcaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanoAcao() throws Exception {
        // Initialize the database
        planoAcaoRepository.saveAndFlush(planoAcao);

        int databaseSizeBeforeDelete = planoAcaoRepository.findAll().size();

        // Delete the planoAcao
        restPlanoAcaoMockMvc.perform(delete("/api/planos-acao/{id}", planoAcao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanoAcao> planoAcaoList = planoAcaoRepository.findAll();
        assertThat(planoAcaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
