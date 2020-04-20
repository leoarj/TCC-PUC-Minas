package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class IncidenteResultadoProcessamentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidenteResultadoProcessamentoDTO.class);
        IncidenteResultadoProcessamentoDTO incidenteResultadoProcessamentoDTO1 = new IncidenteResultadoProcessamentoDTO();
        incidenteResultadoProcessamentoDTO1.setId(1L);
        IncidenteResultadoProcessamentoDTO incidenteResultadoProcessamentoDTO2 = new IncidenteResultadoProcessamentoDTO();
        assertThat(incidenteResultadoProcessamentoDTO1).isNotEqualTo(incidenteResultadoProcessamentoDTO2);
        incidenteResultadoProcessamentoDTO2.setId(incidenteResultadoProcessamentoDTO1.getId());
        assertThat(incidenteResultadoProcessamentoDTO1).isEqualTo(incidenteResultadoProcessamentoDTO2);
        incidenteResultadoProcessamentoDTO2.setId(2L);
        assertThat(incidenteResultadoProcessamentoDTO1).isNotEqualTo(incidenteResultadoProcessamentoDTO2);
        incidenteResultadoProcessamentoDTO1.setId(null);
        assertThat(incidenteResultadoProcessamentoDTO1).isNotEqualTo(incidenteResultadoProcessamentoDTO2);
    }
}
