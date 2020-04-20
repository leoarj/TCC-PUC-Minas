package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class IncidenteResultadoProcessamentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidenteResultadoProcessamento.class);
        IncidenteResultadoProcessamento incidenteResultadoProcessamento1 = new IncidenteResultadoProcessamento();
        incidenteResultadoProcessamento1.setId(1L);
        IncidenteResultadoProcessamento incidenteResultadoProcessamento2 = new IncidenteResultadoProcessamento();
        incidenteResultadoProcessamento2.setId(incidenteResultadoProcessamento1.getId());
        assertThat(incidenteResultadoProcessamento1).isEqualTo(incidenteResultadoProcessamento2);
        incidenteResultadoProcessamento2.setId(2L);
        assertThat(incidenteResultadoProcessamento1).isNotEqualTo(incidenteResultadoProcessamento2);
        incidenteResultadoProcessamento1.setId(null);
        assertThat(incidenteResultadoProcessamento1).isNotEqualTo(incidenteResultadoProcessamento2);
    }
}
