package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Time;

public record FilmeDtoPost(
        @NotBlank(message = "O título é obrigatório") String titulo,
        @NotNull(message = "A duração é obrigatória") Time duracao
) implements Serializable {
}
