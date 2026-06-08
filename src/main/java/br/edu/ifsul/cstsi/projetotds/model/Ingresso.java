package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingressos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ingresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int tipo;

    @ManyToOne
    private Sessao sessao;
}
