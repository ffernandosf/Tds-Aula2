package br.edu.ifsul.cstsi.projetotds.model;

import br.edu.ifsul.cstsi.projetotds.BaseAPIIntegracaoTest;
import br.edu.ifsul.cstsi.projetotds.ProjetoTdsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProjetoTdsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IngressoControllerIntegracaoTest extends BaseAPIIntegracaoTest {

    private static final String BASE_URL = "/api/v1/ingressos";

    private SessaoDto prepararSessao() {
        var sala = post("/api/v1/salas", new SalaDtoPost(88, 150), SalaDto.class).getBody();
        var filme = post("/api/v1/filmes", new FilmeDtoPost("Filme Ingresso Teste", Time.valueOf("02:00:00")), FilmeDto.class).getBody();
        var dto = new SessaoDtoPost(new Date(), Time.valueOf("20:00:00"), 30.0, 15.0, 0, sala.id(), filme.id());
        return post("/api/v1/sessoes", dto, SessaoDto.class).getBody();
    }

    private void limparSessao(Long sessaoId) {
        var sessao = get("/api/v1/sessoes/" + sessaoId, SessaoDto.class).getBody();
        delete("/api/v1/sessoes/" + sessaoId);
        if (sessao != null) {
            delete("/api/v1/filmes/" + sessao.filmeId());
            delete("/api/v1/salas/" + sessao.salaId());
        }
    }

    private IngressoDto criarIngresso(Long sessaoId, int tipo) {
        var dto = new IngressoDtoPost(tipo, sessaoId);
        var response = post(BASE_URL, dto, IngressoDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        return response.getBody();
    }

    @Test
    void findAll_deveRetornar200EListaNaoNula() {
        var response = get(BASE_URL, IngressoDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void findById_deveRetornarIngressoCorreto() {
        var sessao = prepararSessao();
        var criado = criarIngresso(sessao.id(), 1);

        var response = get(BASE_URL + "/" + criado.id(), IngressoDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().tipo());
        assertEquals(sessao.id(), response.getBody().sessaoId());

        delete(BASE_URL + "/" + criado.id());
        limparSessao(sessao.id());
    }

    @Test
    void findById_deveRetornar404QuandoNaoExiste() {
        var response = get(BASE_URL + "/999999", IngressoDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void save_deveCriarIngressoInteiraNaSessaoERetornar201() {
        var sessao = prepararSessao();
        // tipo 1 = inteira
        var dto = new IngressoDtoPost(1, sessao.id());

        var response = post(BASE_URL, dto, IngressoDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().tipo());
        assertEquals(sessao.id(), response.getBody().sessaoId());

        var location = response.getHeaders().getLocation().toString();
        delete(location);
        assertEquals(HttpStatus.NOT_FOUND, get(location, IngressoDto.class).getStatusCode());
        limparSessao(sessao.id());
    }

    @Test
    void save_deveCriarIngressoMeiaNaSessaoERetornar201() {
        var sessao = prepararSessao();
        // tipo 2 = meia
        var dto = new IngressoDtoPost(2, sessao.id());

        var response = post(BASE_URL, dto, IngressoDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(2, response.getBody().tipo());

        delete(BASE_URL + "/" + response.getBody().id());
        limparSessao(sessao.id());
    }

    @Test
    void update_deveAtualizarTipoIngressoERetornar200() {
        var sessao = prepararSessao();
        var criado = criarIngresso(sessao.id(), 1);

        // Muda de inteira (1) para meia (2)
        var dtoUpdate = new IngressoDtoPut(2, sessao.id());
        var response = put(BASE_URL + "/" + criado.id(), dtoUpdate, IngressoDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().tipo());

        delete(BASE_URL + "/" + criado.id());
        limparSessao(sessao.id());
    }

    @Test
    void delete_deveRemoverIngressoERetornar200() {
        var sessao = prepararSessao();
        var criado = criarIngresso(sessao.id(), 1);

        var response = delete(BASE_URL + "/" + criado.id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, get(BASE_URL + "/" + criado.id(), IngressoDto.class).getStatusCode());

        limparSessao(sessao.id());
    }
}
