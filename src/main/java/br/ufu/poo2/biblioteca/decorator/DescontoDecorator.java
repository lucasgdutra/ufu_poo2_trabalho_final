package br.ufu.poo2.biblioteca.decorator;

import br.ufu.poo2.biblioteca.strategy.PagamentoStrategy;

public class DescontoDecorator extends PagamentoDecorator {
    public DescontoDecorator(PagamentoStrategy pagamentoStrategy) {
        super(pagamentoStrategy);
    }

    @Override
    public float calcularPagamento(int diasAtraso) {
        float pagamentoBase = pagamentoStrategy.calcularPagamento(diasAtraso);
        return pagamentoBase - (pagamentoBase * 0.1f);
    }
}
