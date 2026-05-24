package br.com.fiap.oscar_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.oscar_api.model.Ator;
import br.com.fiap.oscar_api.repository.AtorRepository;

@Service
public class AtorService {

    @Autowired
    private AtorRepository repository;

    // Insert into
    public Ator createOrUpdate(Ator ator) {
        return repository.save(ator);
    }

    // Select *
    public List<Ator> findAll() {
        return repository.findAll();
    }

    // Select
    public Optional<Ator> findById(Long id) {
        return repository.findById(id);
    }

    // Delete
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
