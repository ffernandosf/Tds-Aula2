package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;

public record IngressoDtoPut(int tipo, Long sessaoId) implements Serializable {
    public IngressoDtoPut(Ingresso ingresso) {
        this(ingresso.getTipo(),
                ingresso.getSessao() != null ? ingresso.getSessao().getId() : null);
    }
}
