package br.edu.ifsul.cstsi.projetotds.model;

import br.edu.ifsul.cstsi.projetotds.BaseAPIIntegracaoTest;
import br.edu.ifsul.cstsi.projetotds.ProjetoTdsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProjetoTdsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FilmeControllerIntegracaoTest extends BaseAPIIntegracaoTest {

    private static final String BASE_URL = "/api/v1/filmes";

    private FilmeDto criarFilme(String titulo, String duracao) {
        var dto = new FilmeDtoPost(titulo, Time.valueOf(duracao));
        var response = post(BASE_URL, dto, FilmeDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        return response.getBody();
    }

    @Test
    void findAll_deveRetornar200EListaNaoNula() {
        var response = get(BASE_URL, FilmeDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void findById_deveRetornarFilmeCorreto() {
        var criado = criarFilme("Oppenheimer", "03:00:00");

        var response = get(BASE_URL + "/" + criado.id(), FilmeDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Oppenheimer", response.getBody().titulo());

        delete(BASE_URL + "/" + criado.id(), null);
    }

    @Test
    void findById_deveRetornar404QuandoNaoExiste() {
        var response = get(BASE_URL + "/999999", FilmeDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByTitulo_deveRetornarFilmesComPrefixo() {
        var criado = criarFilme("Vingadores", "02:30:00");

        var response = get(BASE_URL + "/titulo/Ving", FilmeDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        assertEquals("Vingadores", response.getBody()[0].titulo());

        delete(BASE_URL + "/" + criado.id(), null);
    }

    @Test
    void save_deveCriarFilmeERetornar201() {
        var dto = new FilmeDtoPost("Interestelar", Time.valueOf("02:49:00"));

        var response = post(BASE_URL, dto, FilmeDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dto.titulo(), response.getBody().titulo());
        assertEquals(dto.duracao(), response.getBody().duracao());

        // Verifica Location header e busca pelo recurso criado
        var location = response.getHeaders().getLocation().toString();
        var buscado = get(location, FilmeDto.class).getBody();
        assertEquals(dto.titulo(), buscado.titulo());

        // Limpeza
        delete(location, null);
        assertEquals(HttpStatus.NOT_FOUND, get(location, FilmeDto.class).getStatusCode());
    }

    @Test
    void update_deveAtualizarFilmeERetornar200() {
        var criado = criarFilme("Titulo Antigo", "01:30:00");

        var dtoUpdate = new FilmeDtoPut("Titulo Atualizado", Time.valueOf("02:00:00"));
        var response = put(BASE_URL + "/" + criado.id(), dtoUpdate, FilmeDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Titulo Atualizado", response.getBody().titulo());
        assertEquals(Time.valueOf("02:00:00"), response.getBody().duracao());

        delete(BASE_URL + "/" + criado.id(), null);
    }

    @Test
    void delete_deveRemoverFilmeERetornar200() {
        var criado = criarFilme("Filme para Deletar", "01:00:00");

        var response = delete(BASE_URL + "/" + criado.id(), null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, get(BASE_URL + "/" + criado.id(), FilmeDto.class).getStatusCode());
    }
}
