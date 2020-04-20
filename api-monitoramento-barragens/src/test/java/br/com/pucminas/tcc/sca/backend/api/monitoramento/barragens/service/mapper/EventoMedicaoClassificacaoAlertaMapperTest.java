package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EventoMedicaoClassificacaoAlertaMapperTest {

    private EventoMedicaoClassificacaoAlertaMapper eventoMedicaoClassificacaoAlertaMapper;

    @BeforeEach
    public void setUp() {
        eventoMedicaoClassificacaoAlertaMapper = new EventoMedicaoClassificacaoAlertaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eventoMedicaoClassificacaoAlertaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventoMedicaoClassificacaoAlertaMapper.fromId(null)).isNull();
    }
}
