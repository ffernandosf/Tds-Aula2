package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

public record IngressoDtoPost(
        @PositiveOrZero(message = "O tipo do ingresso é inválido") int tipo,
        @NotNull(message = "A sessão é obrigatória") Long sessaoId
) implements Serializable {
}
