package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.persistence.*;
import java.sql.Time;

@Entity
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Time duracao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Time getDuracao() { return duracao; }
    public void setDuracao(Time duracao) { this.duracao = duracao; }
}
