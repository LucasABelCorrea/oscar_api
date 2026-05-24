package br.com.fiap.oscar_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AtorCreateRequest {
    @NotNull
    private String nome;
    private Integer numFilmes;
    private Integer idade;
    private Integer numOscars;
}
