package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class AfetadoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AfetadoDTO.class);
        AfetadoDTO afetadoDTO1 = new AfetadoDTO();
        afetadoDTO1.setId(1L);
        AfetadoDTO afetadoDTO2 = new AfetadoDTO();
        assertThat(afetadoDTO1).isNotEqualTo(afetadoDTO2);
        afetadoDTO2.setId(afetadoDTO1.getId());
        assertThat(afetadoDTO1).isEqualTo(afetadoDTO2);
        afetadoDTO2.setId(2L);
        assertThat(afetadoDTO1).isNotEqualTo(afetadoDTO2);
        afetadoDTO1.setId(null);
        assertThat(afetadoDTO1).isNotEqualTo(afetadoDTO2);
    }
}
