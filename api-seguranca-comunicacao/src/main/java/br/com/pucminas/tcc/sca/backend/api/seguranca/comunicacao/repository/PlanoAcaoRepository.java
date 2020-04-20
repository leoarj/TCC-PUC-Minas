package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.repository;

import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.PlanoAcao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlanoAcao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoAcaoRepository extends JpaRepository<PlanoAcao, Long> {
	@Query("select p from PlanoAcao p where p.classificacao = ?1")
	Optional<PlanoAcao> findByClassificacao(Integer classificacao);	
}
