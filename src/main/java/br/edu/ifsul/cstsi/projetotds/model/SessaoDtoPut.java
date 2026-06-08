package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public record SessaoDtoPut(Date dtSessao, Time horSessao, double valorInteira, double valorMeia,
                           int encerrada, Long salaId, Long filmeId) implements Serializable {
    public SessaoDtoPut(Sessao sessao) {
        this(sessao.getDtSessao(),
                sessao.getHorSessao(),
                sessao.getValorInteira(),
                sessao.getValorMeia(),
                sessao.getEncerrada(),
                sessao.getSala() != null ? sessao.getSala().getId() : null,
                sessao.getFilme() != null ? sessao.getFilme().getId() : null);
    }
}
