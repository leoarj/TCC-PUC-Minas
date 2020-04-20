package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BarragemMapperTest {

    private BarragemMapper barragemMapper;

    @BeforeEach
    public void setUp() {
        barragemMapper = new BarragemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(barragemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(barragemMapper.fromId(null)).isNull();
    }
}
