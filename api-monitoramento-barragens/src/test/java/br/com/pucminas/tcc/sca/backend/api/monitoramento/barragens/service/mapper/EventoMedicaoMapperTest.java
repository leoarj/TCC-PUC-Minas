package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EventoMedicaoMapperTest {

    private EventoMedicaoMapper eventoMedicaoMapper;

    @BeforeEach
    public void setUp() {
        eventoMedicaoMapper = new EventoMedicaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eventoMedicaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eventoMedicaoMapper.fromId(null)).isNull();
    }
}
