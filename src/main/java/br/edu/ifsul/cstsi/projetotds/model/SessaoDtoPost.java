package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public record SessaoDtoPost(
        @NotNull(message = "A data da sessão é obrigatória") Date dtSessao,
        @NotNull(message = "O horário da sessão é obrigatório") Time horSessao,
        @PositiveOrZero(message = "O valor da inteira não pode ser negativo") double valorInteira,
        @PositiveOrZero(message = "O valor da meia não pode ser negativo") double valorMeia,
        int encerrada,
        @NotNull(message = "A sala é obrigatória") Long salaId,
        @NotNull(message = "O filme é obrigatório") Long filmeId
) implements Serializable {
}
