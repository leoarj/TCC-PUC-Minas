package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Afetado;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Afetado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AfetadoRepository extends JpaRepository<Afetado, Long> {
}
