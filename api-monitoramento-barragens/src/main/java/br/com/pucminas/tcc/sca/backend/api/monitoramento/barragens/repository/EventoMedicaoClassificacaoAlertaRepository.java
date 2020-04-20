package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicaoClassificacaoAlerta;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Sensor;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EventoMedicaoClassificacaoAlerta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventoMedicaoClassificacaoAlertaRepository extends JpaRepository<EventoMedicaoClassificacaoAlerta, Long> {
	@Query("select e from EventoMedicaoClassificacaoAlerta e where (e.tipo = ?1) and (e.intensidade = ?2)")
	Optional<EventoMedicaoClassificacaoAlerta> findByTipoMedicaoAndByItensidade(TipoMedicao tipoMedicao, Integer intensidade);
}
