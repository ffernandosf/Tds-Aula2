package br.edu.ifsul.cstsi.projetotds.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    Optional<List<Filme>> findByTituloStartingWith(String titulo);
}
