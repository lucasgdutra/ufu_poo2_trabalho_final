package br.ufu.poo2.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufu.poo2.biblioteca.model.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

}
