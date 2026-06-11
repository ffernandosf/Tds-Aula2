package br.edu.ifsul.cstsi.projetotds.model;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/sessoes")
public class SessaoController {

    private final SessaoRepository repository;
    private final SalaRepository salaRepository;
    private final FilmeRepository filmeRepository;

    public SessaoController(SessaoRepository repository, SalaRepository salaRepository, FilmeRepository filmeRepository) {
        this.repository = repository;
        this.salaRepository = salaRepository;
        this.filmeRepository = filmeRepository;
    }

    @GetMapping
    public ResponseEntity<List<SessaoDto>> getAll() {
        var sessoes = repository.findAll().stream().map(SessaoDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(sessoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoDto> getById(@PathVariable Long id) {
        var sessao = repository.findById(id);
        if (sessao.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new SessaoDto(sessao.get()));
    }

    @GetMapping("/filme/{titulo}")
    public ResponseEntity<List<SessaoDto>> getByFilme(@PathVariable String titulo) {
        var sessoes = repository.findByFilme_Titulo(titulo);
        if (sessoes.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(sessoes.get().stream().map(SessaoDto::new).collect(Collectors.toList()));
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<SessaoDto> create(@RequestBody @Valid SessaoDtoPost data, UriComponentsBuilder uriBuilder) {
        var sala = salaRepository.findById(data.salaId()).orElse(null);
        var filme = filmeRepository.findById(data.filmeId()).orElse(null);
        var sessao = new Sessao(null, data.dtSessao(), data.horSessao(), data.valorInteira(),
                data.valorMeia(), data.encerrada(), sala, filme, null);
        repository.save(sessao);
        var uri = uriBuilder.path("/api/v1/sessoes/{id}").buildAndExpand(sessao.getId()).toUri();
        return ResponseEntity.created(uri).body(new SessaoDto(sessao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessaoDto> update(@PathVariable Long id, @RequestBody @Valid SessaoDtoPut data) {
        var optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        var sessao = optional.get();
        sessao.setDtSessao(data.dtSessao());
        sessao.setHorSessao(data.horSessao());
        sessao.setValorInteira(data.valorInteira());
        sessao.setValorMeia(data.valorMeia());
        sessao.setEncerrada(data.encerrada());
        if (data.salaId() != null) sessao.setSala(salaRepository.findById(data.salaId()).orElse(null));
        if (data.filmeId() != null) sessao.setFilme(filmeRepository.findById(data.filmeId()).orElse(null));
        repository.save(sessao);
        return ResponseEntity.ok(new SessaoDto(sessao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var optional = repository.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
