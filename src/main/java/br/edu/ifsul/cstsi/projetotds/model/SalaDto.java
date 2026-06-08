package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;

public record SalaDto(Long id, int nrosala, int capacidade) implements Serializable {
    public SalaDto(Sala sala) {
        this(sala.getId(), sala.getNrosala(), sala.getCapacidade());
    }
}
