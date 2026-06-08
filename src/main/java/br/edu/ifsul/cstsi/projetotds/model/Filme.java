package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Time;

@Entity
@Table(name = "filmes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Time duracao;
}
