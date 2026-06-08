package br.edu.ifsul.cstsi.projetotds.infra.exception;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String mensagem) {
        super(mensagem);
    }
}
