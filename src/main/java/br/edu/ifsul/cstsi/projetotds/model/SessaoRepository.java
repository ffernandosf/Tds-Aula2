package br.edu.ifsul.cstsi.projetotds.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    Optional<List<Sessao>> findByFilme_Titulo(String titulo);
}
