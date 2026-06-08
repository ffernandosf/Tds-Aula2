package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sessoes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dtSessao;
    private java.sql.Time horSessao;
    private double valorInteira;
    private double valorMeia;
    private int encerrada;

    @ManyToOne
    private Sala sala;

    @ManyToOne
    private Filme filme;

    @OneToMany(mappedBy = "sessao")
    private List<Ingresso> ingressos;
}
