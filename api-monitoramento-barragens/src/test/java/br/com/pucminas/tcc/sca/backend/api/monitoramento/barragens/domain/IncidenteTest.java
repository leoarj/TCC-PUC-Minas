package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class IncidenteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incidente.class);
        Incidente incidente1 = new Incidente();
        incidente1.setId(1L);
        Incidente incidente2 = new Incidente();
        incidente2.setId(incidente1.getId());
        assertThat(incidente1).isEqualTo(incidente2);
        incidente2.setId(2L);
        assertThat(incidente1).isNotEqualTo(incidente2);
        incidente1.setId(null);
        assertThat(incidente1).isNotEqualTo(incidente2);
    }
}
