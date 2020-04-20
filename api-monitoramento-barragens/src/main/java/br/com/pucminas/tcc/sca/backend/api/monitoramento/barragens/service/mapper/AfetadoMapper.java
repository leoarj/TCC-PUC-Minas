package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;


import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.*;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.AfetadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Afetado} and its DTO {@link AfetadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {BarragemMapper.class})
public interface AfetadoMapper extends EntityMapper<AfetadoDTO, Afetado> {

    @Mapping(source = "barragem.id", target = "barragemId")
    @Mapping(source = "barragem.nome", target = "barragemNome")
    AfetadoDTO toDto(Afetado afetado);

    @Mapping(source = "barragemId", target = "barragem")
    Afetado toEntity(AfetadoDTO afetadoDTO);

    default Afetado fromId(Long id) {
        if (id == null) {
            return null;
        }
        Afetado afetado = new Afetado();
        afetado.setId(id);
        return afetado;
    }
}
