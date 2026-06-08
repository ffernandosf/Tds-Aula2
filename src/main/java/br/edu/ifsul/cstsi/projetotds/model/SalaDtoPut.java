package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;

public record SalaDtoPut(int nrosala, int capacidade) implements Serializable {
    public SalaDtoPut(Sala sala) {
        this(sala.getNrosala(), sala.getCapacidade());
    }
}
