package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.web.rest.TestUtil;

public class PlanoAcaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoAcao.class);
        PlanoAcao planoAcao1 = new PlanoAcao();
        planoAcao1.setId(1L);
        PlanoAcao planoAcao2 = new PlanoAcao();
        planoAcao2.setId(planoAcao1.getId());
        assertThat(planoAcao1).isEqualTo(planoAcao2);
        planoAcao2.setId(2L);
        assertThat(planoAcao1).isNotEqualTo(planoAcao2);
        planoAcao1.setId(null);
        assertThat(planoAcao1).isNotEqualTo(planoAcao2);
    }
}
