package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/salas")
public class SalaController {

    private final SalaRepository repository;

    public SalaController(SalaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<SalaDto>> getAll() {
        var salas = repository.findAll().stream().map(SalaDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDto> getById(@PathVariable Long id) {
        var sala = repository.findById(id);
        if (sala.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new SalaDto(sala.get()));
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<SalaDto> create(@RequestBody @Valid SalaDtoPost data, UriComponentsBuilder uriBuilder) {
        var sala = new Sala(null, data.nrosala(), data.capacidade());
        repository.save(sala);
        var uri = uriBuilder.path("/api/v1/salas/{id}").buildAndExpand(sala.getId()).toUri();
        return ResponseEntity.created(uri).body(new SalaDto(sala));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDto> update(@PathVariable Long id, @RequestBody @Valid SalaDtoPut data) {
        var optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        var sala = optional.get();
        sala.setNrosala(data.nrosala());
        sala.setCapacidade(data.capacidade());
        repository.save(sala);
        return ResponseEntity.ok(new SalaDto(sala));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
