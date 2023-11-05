package br.ufu.poo2.biblioteca.strategy;

public class PagamentoPix implements PagamentoStrategy {

    @Override
    public boolean realizarPagamento(double valor) {
        return false;
    }

}
