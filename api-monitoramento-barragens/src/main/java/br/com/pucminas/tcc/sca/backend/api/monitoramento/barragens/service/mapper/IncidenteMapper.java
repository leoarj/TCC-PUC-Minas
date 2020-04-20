package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;


import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.*;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.IncidenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Incidente} and its DTO {@link IncidenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {BarragemMapper.class})
public interface IncidenteMapper extends EntityMapper<IncidenteDTO, Incidente> {

    @Mapping(source = "barragem.id", target = "barragemId")
    @Mapping(source = "barragem.nome", target = "barragemNome")
    IncidenteDTO toDto(Incidente incidente);

    @Mapping(source = "barragemId", target = "barragem")
    Incidente toEntity(IncidenteDTO incidenteDTO);

    default Incidente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Incidente incidente = new Incidente();
        incidente.setId(id);
        return incidente;
    }
}
