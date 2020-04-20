package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AfetadoMapperTest {

    private AfetadoMapper afetadoMapper;

    @BeforeEach
    public void setUp() {
        afetadoMapper = new AfetadoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(afetadoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(afetadoMapper.fromId(null)).isNull();
    }
}
