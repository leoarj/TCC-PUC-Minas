package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class EventoMedicaoClassificacaoAlertaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoMedicaoClassificacaoAlerta.class);
        EventoMedicaoClassificacaoAlerta eventoMedicaoClassificacaoAlerta1 = new EventoMedicaoClassificacaoAlerta();
        eventoMedicaoClassificacaoAlerta1.setId(1L);
        EventoMedicaoClassificacaoAlerta eventoMedicaoClassificacaoAlerta2 = new EventoMedicaoClassificacaoAlerta();
        eventoMedicaoClassificacaoAlerta2.setId(eventoMedicaoClassificacaoAlerta1.getId());
        assertThat(eventoMedicaoClassificacaoAlerta1).isEqualTo(eventoMedicaoClassificacaoAlerta2);
        eventoMedicaoClassificacaoAlerta2.setId(2L);
        assertThat(eventoMedicaoClassificacaoAlerta1).isNotEqualTo(eventoMedicaoClassificacaoAlerta2);
        eventoMedicaoClassificacaoAlerta1.setId(null);
        assertThat(eventoMedicaoClassificacaoAlerta1).isNotEqualTo(eventoMedicaoClassificacaoAlerta2);
    }
}
