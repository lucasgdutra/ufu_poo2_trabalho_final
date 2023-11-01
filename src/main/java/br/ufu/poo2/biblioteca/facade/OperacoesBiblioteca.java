package br.ufu.poo2.biblioteca.facade;

import br.ufu.poo2.biblioteca.model.Livro;
import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.service.ServicoEmprestimo;

public class OperacoesBiblioteca {
    private ServicoEmprestimo servicoEmprestimo;
    // ... outros serviços

    public OperacoesBiblioteca(ServicoEmprestimo servicoEmprestimo) {
        this.servicoEmprestimo = servicoEmprestimo;
    }

    public void realizarEmprestimo(Livro livro, Usuario usuario) {
        servicoEmprestimo.emprestar(livro, usuario);
        // ... outras operações relacionadas, como notificações, etc.
    }

    // ... outros métodos relacionados à biblioteca
}
