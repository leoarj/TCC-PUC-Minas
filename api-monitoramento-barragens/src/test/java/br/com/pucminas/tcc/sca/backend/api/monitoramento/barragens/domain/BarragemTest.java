package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class BarragemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Barragem.class);
        Barragem barragem1 = new Barragem();
        barragem1.setId(1L);
        Barragem barragem2 = new Barragem();
        barragem2.setId(barragem1.getId());
        assertThat(barragem1).isEqualTo(barragem2);
        barragem2.setId(2L);
        assertThat(barragem1).isNotEqualTo(barragem2);
        barragem1.setId(null);
        assertThat(barragem1).isNotEqualTo(barragem2);
    }
}
