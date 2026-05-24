package br.com.fiap.oscar_api.dto;

import lombok.Data;

@Data
public class FilmeUpdateRequest {
    private String nome;
    private Integer numPremiacoes;
    private Integer qtdCategoriasDisputadas;
    private String categoria;
}
