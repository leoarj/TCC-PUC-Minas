package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SensorMapperTest {

    private SensorMapper sensorMapper;

    @BeforeEach
    public void setUp() {
        sensorMapper = new SensorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sensorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sensorMapper.fromId(null)).isNull();
    }
}
