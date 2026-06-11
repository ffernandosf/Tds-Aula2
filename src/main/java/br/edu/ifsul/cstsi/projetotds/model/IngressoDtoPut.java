package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

public record IngressoDtoPut(
        @PositiveOrZero(message = "O tipo do ingresso é inválido") int tipo,
        Long sessaoId
) implements Serializable {
    public IngressoDtoPut(Ingresso ingresso) {
        this(ingresso.getTipo(),
                ingresso.getSessao() != null ? ingresso.getSessao().getId() : null);
    }
}
