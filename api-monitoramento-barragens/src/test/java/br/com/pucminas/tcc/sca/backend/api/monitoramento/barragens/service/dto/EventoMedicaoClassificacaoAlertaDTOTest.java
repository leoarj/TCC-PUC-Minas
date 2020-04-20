package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.web.rest.TestUtil;

public class EventoMedicaoClassificacaoAlertaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventoMedicaoClassificacaoAlertaDTO.class);
        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO1 = new EventoMedicaoClassificacaoAlertaDTO();
        eventoMedicaoClassificacaoAlertaDTO1.setId(1L);
        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO2 = new EventoMedicaoClassificacaoAlertaDTO();
        assertThat(eventoMedicaoClassificacaoAlertaDTO1).isNotEqualTo(eventoMedicaoClassificacaoAlertaDTO2);
        eventoMedicaoClassificacaoAlertaDTO2.setId(eventoMedicaoClassificacaoAlertaDTO1.getId());
        assertThat(eventoMedicaoClassificacaoAlertaDTO1).isEqualTo(eventoMedicaoClassificacaoAlertaDTO2);
        eventoMedicaoClassificacaoAlertaDTO2.setId(2L);
        assertThat(eventoMedicaoClassificacaoAlertaDTO1).isNotEqualTo(eventoMedicaoClassificacaoAlertaDTO2);
        eventoMedicaoClassificacaoAlertaDTO1.setId(null);
        assertThat(eventoMedicaoClassificacaoAlertaDTO1).isNotEqualTo(eventoMedicaoClassificacaoAlertaDTO2);
    }
}
