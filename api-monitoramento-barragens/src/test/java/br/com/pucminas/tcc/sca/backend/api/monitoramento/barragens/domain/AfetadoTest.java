package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class AfetadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Afetado.class);
        Afetado afetado1 = new Afetado();
        afetado1.setId(1L);
        Afetado afetado2 = new Afetado();
        afetado2.setId(afetado1.getId());
        assertThat(afetado1).isEqualTo(afetado2);
        afetado2.setId(2L);
        assertThat(afetado1).isNotEqualTo(afetado2);
        afetado1.setId(null);
        assertThat(afetado1).isNotEqualTo(afetado2);
    }
}
