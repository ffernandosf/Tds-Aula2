package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nrosala;
    private int capacidade;
}
