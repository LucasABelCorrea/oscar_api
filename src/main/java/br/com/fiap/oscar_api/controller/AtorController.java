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

import br.com.fiap.oscar_api.model.Ator;
import br.com.fiap.oscar_api.repository.AtorRepository;

@RestController
@RequestMapping("api/${api.version}/atores")
public class AtorController {
   
    @Autowired
    private AtorRepository repository;

    //Insert into
    @PostMapping("")
    public ResponseEntity<Ator> create (@RequestBody Ator ator) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(ator));
    }

    //Select *
    @GetMapping("")
    public ResponseEntity<List<Ator>> findAll () {
        return ResponseEntity.ok(repository.findAll());
    }

    //Select 
    @GetMapping("/{id}")
    public ResponseEntity<Ator> findById (@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Ator> update (@PathVariable Long id, @RequestBody Ator ator) {
        Optional<Ator> optAtor = repository.findById(id);

        if (optAtor.isPresent()) {
            ator.setId(id);
            Ator atorAtualizado = repository.save(ator);
            return ResponseEntity.ok(atorAtualizado);
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
