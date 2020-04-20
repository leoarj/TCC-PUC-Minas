package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class BarragemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarragemDTO.class);
        BarragemDTO barragemDTO1 = new BarragemDTO();
        barragemDTO1.setId(1L);
        BarragemDTO barragemDTO2 = new BarragemDTO();
        assertThat(barragemDTO1).isNotEqualTo(barragemDTO2);
        barragemDTO2.setId(barragemDTO1.getId());
        assertThat(barragemDTO1).isEqualTo(barragemDTO2);
        barragemDTO2.setId(2L);
        assertThat(barragemDTO1).isNotEqualTo(barragemDTO2);
        barragemDTO1.setId(null);
        assertThat(barragemDTO1).isNotEqualTo(barragemDTO2);
    }
}
