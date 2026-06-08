package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;
import java.sql.Time;

public record FilmeDtoPut(String titulo, Time duracao) implements Serializable {
    public FilmeDtoPut(Filme filme) {
        this(filme.getTitulo(), filme.getDuracao());
    }
}
