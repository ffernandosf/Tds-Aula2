package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;

public record IngressoDto(Long id, int tipo, Long sessaoId) implements Serializable {
    public IngressoDto(Ingresso ingresso) {
        this(ingresso.getId(),
                ingresso.getTipo(),
                ingresso.getSessao() != null ? ingresso.getSessao().getId() : null);
    }
}
