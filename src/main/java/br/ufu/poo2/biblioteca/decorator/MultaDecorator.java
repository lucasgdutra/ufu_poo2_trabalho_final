package br.ufu.poo2.biblioteca.decorator;

import br.ufu.poo2.biblioteca.strategy.PagamentoStrategy;

public class MultaDecorator extends PagamentoDecorator {
    public MultaDecorator(PagamentoStrategy pagamentoStrategy) {
        super(pagamentoStrategy);
    }

    @Override
    public float calcularPagamento(int diasAtraso) {
        float pagamentoBase = pagamentoStrategy.calcularPagamento(diasAtraso);
        return pagamentoBase + 1.5f + (0.05f * diasAtraso);
    }

}
