package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/filmes")
public class FilmeController {

    private final FilmeRepository repository;

    public FilmeController(FilmeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<FilmeDto>> getAll() {
        var filmes = repository.findAll().stream().map(FilmeDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeDto> getById(@PathVariable Long id) {
        var filme = repository.findById(id);
        if (filme.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new FilmeDto(filme.get()));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<FilmeDto>> getByTitulo(@PathVariable String titulo) {
        var filmes = repository.findByTituloStartingWith(titulo);
        if (filmes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(filmes.get().stream().map(FilmeDto::new).collect(Collectors.toList()));
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<FilmeDto> create(@RequestBody @Valid FilmeDtoPost data, UriComponentsBuilder uriBuilder) {
        var filme = new Filme(null, data.titulo(), data.duracao());
        repository.save(filme);
        var uri = uriBuilder.path("/api/v1/filmes/{id}").buildAndExpand(filme.getId()).toUri();
        return ResponseEntity.created(uri).body(new FilmeDto(filme));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeDto> update(@PathVariable Long id, @RequestBody @Valid FilmeDtoPut data) {
        var optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        var filme = optional.get();
        filme.setTitulo(data.titulo());
        filme.setDuracao(data.duracao());
        repository.save(filme);
        return ResponseEntity.ok(new FilmeDto(filme));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
