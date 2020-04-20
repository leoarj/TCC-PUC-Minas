package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.web.rest.TestUtil;

public class PlanoAcaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoAcaoDTO.class);
        PlanoAcaoDTO planoAcaoDTO1 = new PlanoAcaoDTO();
        planoAcaoDTO1.setId(1L);
        PlanoAcaoDTO planoAcaoDTO2 = new PlanoAcaoDTO();
        assertThat(planoAcaoDTO1).isNotEqualTo(planoAcaoDTO2);
        planoAcaoDTO2.setId(planoAcaoDTO1.getId());
        assertThat(planoAcaoDTO1).isEqualTo(planoAcaoDTO2);
        planoAcaoDTO2.setId(2L);
        assertThat(planoAcaoDTO1).isNotEqualTo(planoAcaoDTO2);
        planoAcaoDTO1.setId(null);
        assertThat(planoAcaoDTO1).isNotEqualTo(planoAcaoDTO2);
    }
}
