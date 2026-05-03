package br.com.fiap.oscar_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Ator {

    @Id
    Long id;

    @Column (name = "nome_ator", length = 100, nullable = false)
    String nome;
    
    @Column (nullable = false)
    Integer numFilmes;
}
