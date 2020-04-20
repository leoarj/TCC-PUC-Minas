package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class SensorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorDTO.class);
        SensorDTO sensorDTO1 = new SensorDTO();
        sensorDTO1.setId(1L);
        SensorDTO sensorDTO2 = new SensorDTO();
        assertThat(sensorDTO1).isNotEqualTo(sensorDTO2);
        sensorDTO2.setId(sensorDTO1.getId());
        assertThat(sensorDTO1).isEqualTo(sensorDTO2);
        sensorDTO2.setId(2L);
        assertThat(sensorDTO1).isNotEqualTo(sensorDTO2);
        sensorDTO1.setId(null);
        assertThat(sensorDTO1).isNotEqualTo(sensorDTO2);
    }
}
