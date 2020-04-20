package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;


import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.*;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.EventoMedicaoClassificacaoAlertaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventoMedicaoClassificacaoAlerta} and its DTO {@link EventoMedicaoClassificacaoAlertaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventoMedicaoClassificacaoAlertaMapper extends EntityMapper<EventoMedicaoClassificacaoAlertaDTO, EventoMedicaoClassificacaoAlerta> {



    default EventoMedicaoClassificacaoAlerta fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventoMedicaoClassificacaoAlerta eventoMedicaoClassificacaoAlerta = new EventoMedicaoClassificacaoAlerta();
        eventoMedicaoClassificacaoAlerta.setId(id);
        return eventoMedicaoClassificacaoAlerta;
    }
}
