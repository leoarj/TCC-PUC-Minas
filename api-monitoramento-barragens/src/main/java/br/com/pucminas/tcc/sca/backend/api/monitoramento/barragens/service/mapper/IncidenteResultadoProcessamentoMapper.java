package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;


import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.*;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteResultadoProcessamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IncidenteResultadoProcessamento} and its DTO {@link IncidenteResultadoProcessamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncidenteResultadoProcessamentoMapper extends EntityMapper<IncidenteResultadoProcessamentoDTO, IncidenteResultadoProcessamento> {



    default IncidenteResultadoProcessamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        IncidenteResultadoProcessamento incidenteResultadoProcessamento = new IncidenteResultadoProcessamento();
        incidenteResultadoProcessamento.setId(id);
        return incidenteResultadoProcessamento;
    }
}
