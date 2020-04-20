package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IncidenteMapperTest {

    private IncidenteMapper incidenteMapper;

    @BeforeEach
    public void setUp() {
        incidenteMapper = new IncidenteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(incidenteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(incidenteMapper.fromId(null)).isNull();
    }
}
