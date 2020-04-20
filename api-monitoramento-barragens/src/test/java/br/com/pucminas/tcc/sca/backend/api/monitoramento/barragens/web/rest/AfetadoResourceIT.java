package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest;

//import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.RedisTestContainerExtension;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.ApiMonitoramentoBarragensApp;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Afetado;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Barragem;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.AfetadoRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.AfetadoService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.AfetadoDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.AfetadoMapper;

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

/**
 * Integration tests for the {@link AfetadoResource} REST controller.
 */
@SpringBootTest(classes = ApiMonitoramentoBarragensApp.class)
//@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class AfetadoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_RESERVA = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_RESERVA = "BBBBBBBBBB";

    @Autowired
    private AfetadoRepository afetadoRepository;

    @Autowired
    private AfetadoMapper afetadoMapper;

    @Autowired
    private AfetadoService afetadoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAfetadoMockMvc;

    private Afetado afetado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afetado createEntity(EntityManager em) {
        Afetado afetado = new Afetado()
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .telefonePrincipal(DEFAULT_TELEFONE_PRINCIPAL)
            .telefoneReserva(DEFAULT_TELEFONE_RESERVA);
        // Add required entity
        Barragem barragem;
        if (TestUtil.findAll(em, Barragem.class).isEmpty()) {
            barragem = BarragemResourceIT.createEntity(em);
            em.persist(barragem);
            em.flush();
        } else {
            barragem = TestUtil.findAll(em, Barragem.class).get(0);
        }
        afetado.setBarragem(barragem);
        return afetado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Afetado createUpdatedEntity(EntityManager em) {
        Afetado afetado = new Afetado()
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneReserva(UPDATED_TELEFONE_RESERVA);
        // Add required entity
        Barragem barragem;
        if (TestUtil.findAll(em, Barragem.class).isEmpty()) {
            barragem = BarragemResourceIT.createUpdatedEntity(em);
            em.persist(barragem);
            em.flush();
        } else {
            barragem = TestUtil.findAll(em, Barragem.class).get(0);
        }
        afetado.setBarragem(barragem);
        return afetado;
    }

    @BeforeEach
    public void initTest() {
        afetado = createEntity(em);
    }

    @Test
    @Transactional
    public void createAfetado() throws Exception {
        int databaseSizeBeforeCreate = afetadoRepository.findAll().size();

        // Create the Afetado
        AfetadoDTO afetadoDTO = afetadoMapper.toDto(afetado);
        restAfetadoMockMvc.perform(post("/api/afetados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afetadoDTO)))
            .andExpect(status().isCreated());

        // Validate the Afetado in the database
        List<Afetado> afetadoList = afetadoRepository.findAll();
        assertThat(afetadoList).hasSize(databaseSizeBeforeCreate + 1);
        Afetado testAfetado = afetadoList.get(afetadoList.size() - 1);
        assertThat(testAfetado.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAfetado.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAfetado.getTelefonePrincipal()).isEqualTo(DEFAULT_TELEFONE_PRINCIPAL);
        assertThat(testAfetado.getTelefoneReserva()).isEqualTo(DEFAULT_TELEFONE_RESERVA);
    }

    @Test
    @Transactional
    public void createAfetadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = afetadoRepository.findAll().size();

        // Create the Afetado with an existing ID
        afetado.setId(1L);
        AfetadoDTO afetadoDTO = afetadoMapper.toDto(afetado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAfetadoMockMvc.perform(post("/api/afetados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afetadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Afetado in the database
        List<Afetado> afetadoList = afetadoRepository.findAll();
        assertThat(afetadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = afetadoRepository.findAll().size();
        // set the field null
        afetado.setNome(null);

        // Create the Afetado, which fails.
        AfetadoDTO afetadoDTO = afetadoMapper.toDto(afetado);

        restAfetadoMockMvc.perform(post("/api/afetados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afetadoDTO)))
            .andExpect(status().isBadRequest());

        List<Afetado> afetadoList = afetadoRepository.findAll();
        assertThat(afetadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = afetadoRepository.findAll().size();
        // set the field null
        afetado.setEmail(null);

        // Create the Afetado, which fails.
        AfetadoDTO afetadoDTO = afetadoMapper.toDto(afetado);

        restAfetadoMockMvc.perform(post("/api/afetados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afetadoDTO)))
            .andExpect(status().isBadRequest());

        List<Afetado> afetadoList = afetadoRepository.findAll();
        assertThat(afetadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonePrincipalIsRequired() throws Exception {
        int databaseSizeBeforeTest = afetadoRepository.findAll().size();
        // set the field null
        afetado.setTelefonePrincipal(null);

        // Create the Afetado, which fails.
        AfetadoDTO afetadoDTO = afetadoMapper.toDto(afetado);

        restAfetadoMockMvc.perform(post("/api/afetados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afetadoDTO)))
            .andExpect(status().isBadRequest());

        List<Afetado> afetadoList = afetadoRepository.findAll();
        assertThat(afetadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefoneReservaIsRequired() throws Exception {
        int databaseSizeBeforeTest = afetadoRepository.findAll().size();
        // set the field null
        afetado.setTelefoneReserva(null);

        // Create the Afetado, which fails.
        AfetadoDTO afetadoDTO = afetadoMapper.toDto(afetado);

        restAfetadoMockMvc.perform(post("/api/afetados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afetadoDTO)))
            .andExpect(status().isBadRequest());

        List<Afetado> afetadoList = afetadoRepository.findAll();
        assertThat(afetadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAfetados() throws Exception {
        // Initialize the database
        afetadoRepository.saveAndFlush(afetado);

        // Get all the afetadoList
        restAfetadoMockMvc.perform(get("/api/afetados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(afetado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefonePrincipal").value(hasItem(DEFAULT_TELEFONE_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefoneReserva").value(hasItem(DEFAULT_TELEFONE_RESERVA)));
    }
    
    @Test
    @Transactional
    public void getAfetado() throws Exception {
        // Initialize the database
        afetadoRepository.saveAndFlush(afetado);

        // Get the afetado
        restAfetadoMockMvc.perform(get("/api/afetados/{id}", afetado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(afetado.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefonePrincipal").value(DEFAULT_TELEFONE_PRINCIPAL))
            .andExpect(jsonPath("$.telefoneReserva").value(DEFAULT_TELEFONE_RESERVA));
    }

    @Test
    @Transactional
    public void getNonExistingAfetado() throws Exception {
        // Get the afetado
        restAfetadoMockMvc.perform(get("/api/afetados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAfetado() throws Exception {
        // Initialize the database
        afetadoRepository.saveAndFlush(afetado);

        int databaseSizeBeforeUpdate = afetadoRepository.findAll().size();

        // Update the afetado
        Afetado updatedAfetado = afetadoRepository.findById(afetado.getId()).get();
        // Disconnect from session so that the updates on updatedAfetado are not directly saved in db
        em.detach(updatedAfetado);
        updatedAfetado
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .telefonePrincipal(UPDATED_TELEFONE_PRINCIPAL)
            .telefoneReserva(UPDATED_TELEFONE_RESERVA);
        AfetadoDTO afetadoDTO = afetadoMapper.toDto(updatedAfetado);

        restAfetadoMockMvc.perform(put("/api/afetados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afetadoDTO)))
            .andExpect(status().isOk());

        // Validate the Afetado in the database
        List<Afetado> afetadoList = afetadoRepository.findAll();
        assertThat(afetadoList).hasSize(databaseSizeBeforeUpdate);
        Afetado testAfetado = afetadoList.get(afetadoList.size() - 1);
        assertThat(testAfetado.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAfetado.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAfetado.getTelefonePrincipal()).isEqualTo(UPDATED_TELEFONE_PRINCIPAL);
        assertThat(testAfetado.getTelefoneReserva()).isEqualTo(UPDATED_TELEFONE_RESERVA);
    }

    @Test
    @Transactional
    public void updateNonExistingAfetado() throws Exception {
        int databaseSizeBeforeUpdate = afetadoRepository.findAll().size();

        // Create the Afetado
        AfetadoDTO afetadoDTO = afetadoMapper.toDto(afetado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfetadoMockMvc.perform(put("/api/afetados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(afetadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Afetado in the database
        List<Afetado> afetadoList = afetadoRepository.findAll();
        assertThat(afetadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAfetado() throws Exception {
        // Initialize the database
        afetadoRepository.saveAndFlush(afetado);

        int databaseSizeBeforeDelete = afetadoRepository.findAll().size();

        // Delete the afetado
        restAfetadoMockMvc.perform(delete("/api/afetados/{id}", afetado.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Afetado> afetadoList = afetadoRepository.findAll();
        assertThat(afetadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
