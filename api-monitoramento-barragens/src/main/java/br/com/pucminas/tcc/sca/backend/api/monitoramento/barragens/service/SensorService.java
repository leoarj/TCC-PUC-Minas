package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Sensor;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository.SensorRepository;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.SensorDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.mapper.SensorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link Sensor}.
 */
@Service
@Transactional
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class SensorService {

    private final Logger log = LoggerFactory.getLogger(SensorService.class);

    private final SensorRepository sensorRepository;

    private final SensorMapper sensorMapper;

    public SensorService(SensorRepository sensorRepository, SensorMapper sensorMapper) {
        this.sensorRepository = sensorRepository;
        this.sensorMapper = sensorMapper;
    }

    /**
     * Save a sensor.
     *
     * @param sensorDTO the entity to save.
     * @return the persisted entity.
     */
    public SensorDTO save(SensorDTO sensorDTO) {
        log.debug("Request to save Sensor : {}", sensorDTO);
        Sensor sensor = sensorMapper.toEntity(sensorDTO);
        
        // Gerar identificador único (UUID)
        if (sensorDTO.getIdentificador() == null || sensorDTO.getIdentificador().toString().equals("00000000-0000-0000-0000-000000000000")) {
        	sensorDTO.setIdentificador(UUID.randomUUID());
        }
        
        sensor = sensorRepository.save(sensor);
        return sensorMapper.toDto(sensor);
    }

    /**
     * Get all the sensors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SensorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sensors");
        return sensorRepository.findAll(pageable)
            .map(sensorMapper::toDto);
    }

    /**
     * Get one sensor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SensorDTO> findOne(Long id) {
        log.debug("Request to get Sensor : {}", id);
        return sensorRepository.findById(id)
            .map(sensorMapper::toDto);
    }

    /**
     * Delete the sensor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sensor : {}", id);
        sensorRepository.deleteById(id);
    }
    
    /**
     * Get one sensor by identificador.
     *
     * @param identificador the UUID of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SensorDTO> findByIdentificador(UUID identificador) {
    	log.debug("Request to get Sensor : {}", identificador);
        return sensorRepository.findByIdentificador(identificador)
            .map(sensorMapper::toDto);
    }
}
