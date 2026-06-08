package br.edu.ifsul.cstsi.projetotds.model;

import java.io.Serializable;

public record IngressoDtoPost(int tipo, Long sessaoId) implements Serializable {
}
