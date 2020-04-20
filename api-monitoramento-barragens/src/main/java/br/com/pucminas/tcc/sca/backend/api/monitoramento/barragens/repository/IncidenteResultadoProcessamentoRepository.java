package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.IncidenteResultadoProcessamento;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IncidenteResultadoProcessamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidenteResultadoProcessamentoRepository extends JpaRepository<IncidenteResultadoProcessamento, Long> {
}
