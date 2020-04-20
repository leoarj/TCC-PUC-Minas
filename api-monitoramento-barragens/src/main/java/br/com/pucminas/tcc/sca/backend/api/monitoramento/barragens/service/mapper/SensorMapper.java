package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper;


import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.*;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.SensorDTO;

import java.util.UUID;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sensor} and its DTO {@link SensorDTO}.
 */
@Mapper(componentModel = "spring", uses = {BarragemMapper.class})
public interface SensorMapper extends EntityMapper<SensorDTO, Sensor> {

    @Mapping(source = "barragem.id", target = "barragemId")
    @Mapping(source = "barragem.nome", target = "barragemNome")
    SensorDTO toDto(Sensor sensor);

    @Mapping(source = "barragemId", target = "barragem")
    Sensor toEntity(SensorDTO sensorDTO);

    default Sensor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sensor sensor = new Sensor();
        sensor.setId(id);
        return sensor;
    }
}
