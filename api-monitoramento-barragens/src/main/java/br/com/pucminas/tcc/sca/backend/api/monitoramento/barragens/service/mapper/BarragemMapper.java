package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;


import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.*;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.BarragemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Barragem} and its DTO {@link BarragemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BarragemMapper extends EntityMapper<BarragemDTO, Barragem> {


    @Mapping(target = "sensors", ignore = true)
    @Mapping(target = "removeSensor", ignore = true)
    @Mapping(target = "incidentes", ignore = true)
    @Mapping(target = "removeIncidente", ignore = true)
    //@Mapping(target = "afetados", ignore = true)
    @Mapping(source = "afetados", target = "afetados")
    @Mapping(target = "removeAfetado", ignore = true)
    Barragem toEntity(BarragemDTO barragemDTO);

    default Barragem fromId(Long id) {
        if (id == null) {
            return null;
        }
        Barragem barragem = new Barragem();
        barragem.setId(id);
        return barragem;
    }
}
