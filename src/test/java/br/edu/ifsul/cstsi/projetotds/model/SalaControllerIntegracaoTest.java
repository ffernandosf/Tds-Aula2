package br.edu.ifsul.cstsi.projetotds.model;

import br.edu.ifsul.cstsi.projetotds.BaseAPIIntegracaoTest;
import br.edu.ifsul.cstsi.projetotds.ProjetoTdsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProjetoTdsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SalaControllerIntegracaoTest extends BaseAPIIntegracaoTest {

    private static final String BASE_URL = "/api/v1/salas";

    private SalaDto criarSala(int nro, int capacidade) {
        var dto = new SalaDtoPost(nro, capacidade);
        var response = post(BASE_URL, dto, SalaDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        return response.getBody();
    }

    @Test
    void findAll_deveRetornar200EListaNaoNula() {
        var response = get(BASE_URL, SalaDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void findById_deveRetornarSalaCorreta() {
        var criada = criarSala(10, 80);

        var response = get(BASE_URL + "/" + criada.id(), SalaDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody().nrosala());
        assertEquals(80, response.getBody().capacidade());

        delete(BASE_URL + "/" + criada.id(), null);
    }

    @Test
    void findById_deveRetornar404QuandoNaoExiste() {
        var response = get(BASE_URL + "/999999", SalaDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void save_deveCriarSalaERetornar201() {
        var dto = new SalaDtoPost(5, 150);

        var response = post(BASE_URL, dto, SalaDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dto.nrosala(), response.getBody().nrosala());
        assertEquals(dto.capacidade(), response.getBody().capacidade());

        var location = response.getHeaders().getLocation().toString();
        var buscada = get(location, SalaDto.class).getBody();
        assertEquals(dto.capacidade(), buscada.capacidade());

        delete(location, null);
        assertEquals(HttpStatus.NOT_FOUND, get(location, SalaDto.class).getStatusCode());
    }

    @Test
    void update_deveAtualizarSalaERetornar200() {
        var criada = criarSala(3, 50);

        var dtoUpdate = new SalaDtoPut(3, 100);
        var response = put(BASE_URL + "/" + criada.id(), dtoUpdate, SalaDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100, response.getBody().capacidade());

        delete(BASE_URL + "/" + criada.id(), null);
    }

    @Test
    void delete_deveRemoverSalaERetornar200() {
        var criada = criarSala(7, 60);

        var response = delete(BASE_URL + "/" + criada.id(), null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, get(BASE_URL + "/" + criada.id(), SalaDto.class).getStatusCode());
    }
}
