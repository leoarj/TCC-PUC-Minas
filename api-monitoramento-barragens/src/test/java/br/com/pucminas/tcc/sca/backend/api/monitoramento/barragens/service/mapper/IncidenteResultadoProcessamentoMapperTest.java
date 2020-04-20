package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IncidenteResultadoProcessamentoMapperTest {

    private IncidenteResultadoProcessamentoMapper incidenteResultadoProcessamentoMapper;

    @BeforeEach
    public void setUp() {
        incidenteResultadoProcessamentoMapper = new IncidenteResultadoProcessamentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(incidenteResultadoProcessamentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(incidenteResultadoProcessamentoMapper.fromId(null)).isNull();
    }
}
