package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.repository;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Barragem;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Afetado;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Barragem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BarragemRepository extends JpaRepository<Barragem, Long> { 
}
