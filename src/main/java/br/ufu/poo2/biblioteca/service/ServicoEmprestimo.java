package br.ufu.poo2.biblioteca.service;

import java.util.Date;

import br.ufu.poo2.biblioteca.model.Emprestimo;
import br.ufu.poo2.biblioteca.model.Livro;
import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.repository.RepositorioEmprestimo;

public class ServicoEmprestimo {
    private RepositorioEmprestimo repositorio;

    public ServicoEmprestimo(RepositorioEmprestimo repositorio) {
        this.repositorio = repositorio;
    }

    public void emprestar(Livro livro, Usuario usuario) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(new Date());
        // ... lógica para definir data de devolução, etc.

        repositorio.salvar(emprestimo);
    }

    // ... outros métodos relacionados ao empréstimo
}
