package br.com.fiap.oscar_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Filme {
    @Id
    Long id;

    @Column (name = "nome_filme", length = 100, nullable = false)
    String nome;

    @Column (length = 30, nullable = false)
    Integer numPremiacoes;

    @Column (length = 30, nullable = false)
    Integer qtdCategoriasDisputadas;
}
