package br.edu.ifsul.cstsi.projetotds;

import br.edu.ifsul.cstsi.projetotds.autenticacao.AutenticacaoService;
import br.edu.ifsul.cstsi.projetotds.infra.security.TokenService;
import br.edu.ifsul.cstsi.projetotds.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(classes = ProjetoTdsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseAPIIntegracaoTest {

    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    private String jwtToken;

    @BeforeEach
    public void configurarAutenticacao() {
        Usuario admin = (Usuario) autenticacaoService.loadUserByUsername("admin@email.com");
        assertNotNull(admin, "Usuário admin não encontrado. Verifique o data.sql.");
        jwtToken = tokenService.geraToken(admin);
        assertNotNull(jwtToken, "Token JWT não foi gerado.");
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    protected <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        return rest.exchange(url, GET, new HttpEntity<>(getHeaders()), responseType);
    }

    protected <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {
        return rest.exchange(url, POST, new HttpEntity<>(body, getHeaders()), responseType);
    }

    protected <T> ResponseEntity<T> put(String url, Object body, Class<T> responseType) {
        return rest.exchange(url, PUT, new HttpEntity<>(body, getHeaders()), responseType);
    }

    protected ResponseEntity<Void> delete(String url) {
        return rest.exchange(url, DELETE, new HttpEntity<>(getHeaders()), Void.class);
    }
}
