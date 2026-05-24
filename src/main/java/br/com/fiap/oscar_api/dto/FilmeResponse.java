package br.com.fiap.oscar_api.dto;

import lombok.Data;

@Data
public class FilmeResponse {
    private Long id;
    private String nome;
    private Integer numPremiacoes;
    private Integer qtdCategoriasDisputadas;
    private String categoria;
}
