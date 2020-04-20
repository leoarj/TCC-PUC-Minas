package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanoAcaoMapperTest {

    private PlanoAcaoMapper planoAcaoMapper;

    @BeforeEach
    public void setUp() {
        planoAcaoMapper = new PlanoAcaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planoAcaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planoAcaoMapper.fromId(null)).isNull();
    }
}
