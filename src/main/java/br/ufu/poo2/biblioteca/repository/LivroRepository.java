package br.ufu.poo2.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ufu.poo2.biblioteca.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
