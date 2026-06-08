package br.edu.ifsul.cstsi.projetotds.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface SalaRepository extends JpaRepository<Sala, Long> {
}
