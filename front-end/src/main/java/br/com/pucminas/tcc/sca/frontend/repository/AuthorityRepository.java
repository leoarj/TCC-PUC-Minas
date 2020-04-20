package br.com.pucminas.tcc.sca.frontend.repository;

import br.com.pucminas.tcc.sca.frontend.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
