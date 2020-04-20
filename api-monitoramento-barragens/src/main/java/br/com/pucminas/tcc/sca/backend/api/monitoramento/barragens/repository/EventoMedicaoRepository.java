package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EventoMedicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventoMedicaoRepository extends JpaRepository<EventoMedicao, Long> {
}
