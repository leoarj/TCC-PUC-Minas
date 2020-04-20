package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class IncidenteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidenteDTO.class);
        IncidenteDTO incidenteDTO1 = new IncidenteDTO();
        incidenteDTO1.setId(1L);
        IncidenteDTO incidenteDTO2 = new IncidenteDTO();
        assertThat(incidenteDTO1).isNotEqualTo(incidenteDTO2);
        incidenteDTO2.setId(incidenteDTO1.getId());
        assertThat(incidenteDTO1).isEqualTo(incidenteDTO2);
        incidenteDTO2.setId(2L);
        assertThat(incidenteDTO1).isNotEqualTo(incidenteDTO2);
        incidenteDTO1.setId(null);
        assertThat(incidenteDTO1).isNotEqualTo(incidenteDTO2);
    }
}
