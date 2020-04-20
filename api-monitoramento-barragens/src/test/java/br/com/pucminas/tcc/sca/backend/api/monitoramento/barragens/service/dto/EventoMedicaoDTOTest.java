package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class EventoMedicaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoMedicaoDTO.class);
        EventoMedicaoDTO eventoMedicaoDTO1 = new EventoMedicaoDTO();
        eventoMedicaoDTO1.setId(1L);
        EventoMedicaoDTO eventoMedicaoDTO2 = new EventoMedicaoDTO();
        assertThat(eventoMedicaoDTO1).isNotEqualTo(eventoMedicaoDTO2);
        eventoMedicaoDTO2.setId(eventoMedicaoDTO1.getId());
        assertThat(eventoMedicaoDTO1).isEqualTo(eventoMedicaoDTO2);
        eventoMedicaoDTO2.setId(2L);
        assertThat(eventoMedicaoDTO1).isNotEqualTo(eventoMedicaoDTO2);
        eventoMedicaoDTO1.setId(null);
        assertThat(eventoMedicaoDTO1).isNotEqualTo(eventoMedicaoDTO2);
    }
}
