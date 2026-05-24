package br.com.fiap.oscar_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "atores")
public class Ator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome_ator", length = 100, nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer numFilmes;

    @Column(length = 3)
    private Integer idade;

    @Column(length = 3, nullable = false)
    private Integer numOscars;
}
