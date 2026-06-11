package br.edu.ifsul.cstsi.projetotds.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioAutenticacaoDTO(
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido") String email,
        @NotBlank(message = "A senha é obrigatória") String senha
) {
}
