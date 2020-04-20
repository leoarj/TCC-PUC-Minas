package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class EventoMedicaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoMedicao.class);
        EventoMedicao eventoMedicao1 = new EventoMedicao();
        eventoMedicao1.setId(1L);
        EventoMedicao eventoMedicao2 = new EventoMedicao();
        eventoMedicao2.setId(eventoMedicao1.getId());
        assertThat(eventoMedicao1).isEqualTo(eventoMedicao2);
        eventoMedicao2.setId(2L);
        assertThat(eventoMedicao1).isNotEqualTo(eventoMedicao2);
        eventoMedicao1.setId(null);
        assertThat(eventoMedicao1).isNotEqualTo(eventoMedicao2);
    }
}
