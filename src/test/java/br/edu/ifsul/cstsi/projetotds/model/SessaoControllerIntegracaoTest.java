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
class SessaoControllerIntegracaoTest extends BaseAPIIntegracaoTest {

    private static final String BASE_URL = "/api/v1/sessoes";

    private FilmeDto criarFilme() {
        var dto = new FilmeDtoPost("Filme Sessao Teste", Time.valueOf("01:45:00"));
        return post("/api/v1/filmes", dto, FilmeDto.class).getBody();
    }

    private SalaDto criarSala() {
        var dto = new SalaDtoPost(99, 200);
        return post("/api/v1/salas", dto, SalaDto.class).getBody();
    }

    private SessaoDto criarSessao(Long salaId, Long filmeId) {
        var dto = new SessaoDtoPost(
                new Date(), Time.valueOf("21:00:00"),
                40.0, 20.0, 0, salaId, filmeId
        );
        var response = post(BASE_URL, dto, SessaoDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        return response.getBody();
    }

    @Test
    void findAll_deveRetornar200EListaNaoNula() {
        var response = get(BASE_URL, SessaoDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void findById_deveRetornarSessaoCorreta() {
        var sala = criarSala();
        var filme = criarFilme();
        var criada = criarSessao(sala.id(), filme.id());

        var response = get(BASE_URL + "/" + criada.id(), SessaoDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sala.id(), response.getBody().salaId());
        assertEquals(filme.id(), response.getBody().filmeId());
        assertEquals(40.0, response.getBody().valorInteira());

        delete(BASE_URL + "/" + criada.id());
        delete("/api/v1/filmes/" + filme.id());
        delete("/api/v1/salas/" + sala.id());
    }

    @Test
    void findById_deveRetornar404QuandoNaoExiste() {
        var response = get(BASE_URL + "/999999", SessaoDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void save_deveCriarSessaoERetornar201() {
        var sala = criarSala();
        var filme = criarFilme();

        var dto = new SessaoDtoPost(
                new Date(), Time.valueOf("18:30:00"),
                35.0, 17.50, 0, sala.id(), filme.id()
        );

        var response = post(BASE_URL, dto, SessaoDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(35.0, response.getBody().valorInteira());
        assertEquals(17.50, response.getBody().valorMeia());

        var location = response.getHeaders().getLocation().toString();
        delete(location);
        delete("/api/v1/filmes/" + filme.id());
        delete("/api/v1/salas/" + sala.id());
    }

    @Test
    void update_deveAtualizarValoresSessao() {
        var sala = criarSala();
        var filme = criarFilme();
        var criada = criarSessao(sala.id(), filme.id());

        var dtoUpdate = new SessaoDtoPut(
                new Date(), Time.valueOf("22:00:00"),
                50.0, 25.0, 0, sala.id(), filme.id()
        );
        var response = put(BASE_URL + "/" + criada.id(), dtoUpdate, SessaoDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(50.0, response.getBody().valorInteira());
        assertEquals(25.0, response.getBody().valorMeia());

        delete(BASE_URL + "/" + criada.id());
        delete("/api/v1/filmes/" + filme.id());
        delete("/api/v1/salas/" + sala.id());
    }

    @Test
    void delete_deveRemoverSessaoERetornar200() {
        var sala = criarSala();
        var filme = criarFilme();
        var criada = criarSessao(sala.id(), filme.id());

        var response = delete(BASE_URL + "/" + criada.id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, get(BASE_URL + "/" + criada.id(), SessaoDto.class).getStatusCode());

        delete("/api/v1/filmes/" + filme.id());
        delete("/api/v1/salas/" + sala.id());
    }
}
