package br.com.fiap.oscar_api.dto;

import lombok.Data;

@Data
public class AtorResponse {
    private Long id;
    private String nome;
    private Integer numFilmes;
    private Integer idade;
    private Integer numOscars;
}
