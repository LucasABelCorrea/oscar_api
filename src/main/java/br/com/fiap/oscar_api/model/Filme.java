package br.com.fiap.oscar_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table (name = "filmes")
public class Filme {
    @Id
    private Long id;

    @Column (name = "nome_filme", length = 100, nullable = false)
    private String nome;

    @Column (length = 30, nullable = false)
    private Integer numPremiacoes;

    @Column (length = 30, nullable = false)
    private Integer qtdCategoriasDisputadas;

    @Column(length = 100, nullable = false)
    private String categoria;
}
