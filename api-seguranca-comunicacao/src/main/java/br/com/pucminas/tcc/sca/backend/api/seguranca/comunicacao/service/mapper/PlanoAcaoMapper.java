package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.mapper;


import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.*;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.PlanoAcaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoAcao} and its DTO {@link PlanoAcaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlanoAcaoMapper extends EntityMapper<PlanoAcaoDTO, PlanoAcao> {



    default PlanoAcao fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoAcao planoAcao = new PlanoAcao();
        planoAcao.setId(id);
        return planoAcao;
    }
}
