package br.com.fiap.oscar_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class AtoresController {
    @GetMapping("/melhor-atriz")
    public String melhorAtriz() {
        return "A vencedora da categoria Melhor Atriz foi: Jessie Buckley";
    }

    @GetMapping("/melhor-ator")
    public String melhorAtor() {
        return "O vencedor da categoria Melhor Ator foi: Michael B. Jordan";
    }

}
