package br.com.fiap.oscar_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilmeCreateRequest {
    @NotNull
    private String nome;
    private Integer numPremiacoes;
    private Integer qtdCategoriasDisputadas;
    private String categoria;
}
