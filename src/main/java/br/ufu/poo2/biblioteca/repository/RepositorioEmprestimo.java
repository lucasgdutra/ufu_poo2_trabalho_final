package br.ufu.poo2.biblioteca.repository;

import br.ufu.poo2.biblioteca.model.Emprestimo;

public interface RepositorioEmprestimo {
    void salvar(Emprestimo emprestimo);

    Emprestimo buscarPorId(Long id);
    // ... outros métodos CRUD
}
