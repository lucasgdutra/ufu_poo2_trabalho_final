package br.ufu.poo2.biblioteca.decorator;

import br.ufu.poo2.biblioteca.strategy.PagamentoStrategy;

public abstract class PagamentoDecorator implements PagamentoStrategy {
    protected PagamentoStrategy pagamentoStrategy;

    public PagamentoDecorator(PagamentoStrategy pagamentoStrategy) {
        this.pagamentoStrategy = pagamentoStrategy;
    }

    @Override
    public abstract float calcularPagamento(int diasAtraso);
}
