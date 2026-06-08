package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public record SessaoDtoPost(Date dtSessao, Time horSessao, double valorInteira, double valorMeia,
                            int encerrada, Long salaId, Long filmeId) implements Serializable {
}
