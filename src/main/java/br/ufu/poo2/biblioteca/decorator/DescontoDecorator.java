package br.ufu.poo2.biblioteca.decorator;

import br.ufu.poo2.biblioteca.model.Emprestimo;


public class DescontoDecorator {
    private Emprestimo emprestimo;

    public DescontoDecorator(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;

    }

    public float calcularPagamento() {
        float pagamentoBase = emprestimo.calcularPagamento();
        return pagamentoBase - (0.1f * pagamentoBase);
    }
}
