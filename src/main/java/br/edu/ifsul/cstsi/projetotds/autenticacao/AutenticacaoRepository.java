package br.edu.ifsul.cstsi.projetotds.autenticacao;

import br.edu.ifsul.cstsi.projetotds.usuario.Usuario;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AutenticacaoRepository extends Repository<Usuario, Long> {
    Usuario findByEmail(String email);
}
