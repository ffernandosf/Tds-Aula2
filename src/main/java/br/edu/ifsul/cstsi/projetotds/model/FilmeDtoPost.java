package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;
import java.sql.Time;

public record FilmeDtoPost(String titulo, Time duracao) implements Serializable {
}
