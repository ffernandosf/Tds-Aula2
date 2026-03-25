package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.persistence.*;

@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nrosala;
    private int capacidade;

}
