package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public record SalaDtoPut(
        @Positive(message = "O número da sala deve ser positivo") int nrosala,
        @Positive(message = "A capacidade deve ser maior que zero") int capacidade
) implements Serializable {
    public SalaDtoPut(Sala sala) {
        this(sala.getNrosala(), sala.getCapacidade());
    }
}
