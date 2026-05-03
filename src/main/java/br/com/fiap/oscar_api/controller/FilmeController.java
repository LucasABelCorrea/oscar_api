package br.com.fiap.oscar_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.oscar_api.model.Filme;
import br.com.fiap.oscar_api.repository.FilmeRepository;

@RestController
@RequestMapping("api/${api.version}/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository repository;

    //Insert into
    @PostMapping("")
    public ResponseEntity<Filme> create (@RequestBody Filme filme) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(filme));
    }

    //Select *
    @GetMapping("")
    public ResponseEntity<List<Filme>> findAll () {
        return ResponseEntity.ok(repository.findAll());
    }

    //Select 
    @GetMapping("/{id}")
    public ResponseEntity<Filme> findById (@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Filme> update (@PathVariable Long id, @RequestBody Filme filme) {
        Optional<Filme> optFilme = repository.findById(id);

        if (optFilme.isPresent()) {
            filme.setId(id);
            Filme filmeAtualizado = repository.save(filme);
            return ResponseEntity.ok(filmeAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById (@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
