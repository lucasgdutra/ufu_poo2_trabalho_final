package br.ufu.poo2.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ISBN;
    private String titulo;
    private String autor;
    private String editora;
    private int ano;
    private int quantidadeTotal;
    private int quantidadeDisponivel;

    public void Emprestar() {
        if (quantidadeDisponivel <= 0) {
            throw new IllegalArgumentException("Não há livros disponíveis");
        }
        quantidadeDisponivel--;
    }

    public void Devolver() {
        quantidadeDisponivel++;
    }

}
