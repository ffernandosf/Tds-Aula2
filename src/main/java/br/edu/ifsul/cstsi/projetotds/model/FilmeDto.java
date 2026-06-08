package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;
import java.sql.Time;

public record FilmeDto(Long id, String titulo, Time duracao) implements Serializable {
    public FilmeDto(Filme filme) {
        this(filme.getId(), filme.getTitulo(), filme.getDuracao());
    }
}
