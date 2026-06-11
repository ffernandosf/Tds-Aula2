package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/ingressos")
public class IngressoController {

    private final IngressoRepository repository;
    private final SessaoRepository sessaoRepository;

    public IngressoController(IngressoRepository repository, SessaoRepository sessaoRepository) {
        this.repository = repository;
        this.sessaoRepository = sessaoRepository;
    }

    @GetMapping
    public ResponseEntity<List<IngressoDto>> getAll() {
        var ingressos = repository.findAll().stream().map(IngressoDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(ingressos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngressoDto> getById(@PathVariable Long id) {
        var ingresso = repository.findById(id);
        if (ingresso.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new IngressoDto(ingresso.get()));
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<IngressoDto> create(@RequestBody @Valid IngressoDtoPost data, UriComponentsBuilder uriBuilder) {
        var sessao = sessaoRepository.findById(data.sessaoId()).orElse(null);
        var ingresso = new Ingresso(null, data.tipo(), sessao);
        repository.save(ingresso);
        var uri = uriBuilder.path("/api/v1/ingressos/{id}").buildAndExpand(ingresso.getId()).toUri();
        return ResponseEntity.created(uri).body(new IngressoDto(ingresso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngressoDto> update(@PathVariable Long id, @RequestBody @Valid IngressoDtoPut data) {
        var optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        var ingresso = optional.get();
        ingresso.setTipo(data.tipo());
        if (data.sessaoId() != null) ingresso.setSessao(sessaoRepository.findById(data.sessaoId()).orElse(null));
        repository.save(ingresso);
        return ResponseEntity.ok(new IngressoDto(ingresso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
