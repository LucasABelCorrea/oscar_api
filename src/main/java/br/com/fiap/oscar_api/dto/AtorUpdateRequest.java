package br.com.fiap.oscar_api.dto;

import lombok.Data;

@Data
public class AtorUpdateRequest {
    private String nome;
    private Integer numFilmes;
    private Integer idade;
    private Integer numOscars;
}
