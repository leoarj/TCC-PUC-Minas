package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;


import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.*;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventoMedicao} and its DTO {@link EventoMedicaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventoMedicaoMapper extends EntityMapper<EventoMedicaoDTO, EventoMedicao> {



    default EventoMedicao fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventoMedicao eventoMedicao = new EventoMedicao();
        eventoMedicao.setId(id);
        return eventoMedicao;
    }
}
