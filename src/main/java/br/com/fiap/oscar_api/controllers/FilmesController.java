package br.com.fiap.oscar_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class FilmesController {

    @GetMapping("/melhor-filme")
    public String melhorFilme() {
        return ("O vencedor da categoria Melhor Filme foi: Uma Batalha Após a Outra.");
    }
}
