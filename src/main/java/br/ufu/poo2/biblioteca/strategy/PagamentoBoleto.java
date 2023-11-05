package br.ufu.poo2.biblioteca.strategy;

public class PagamentoBoleto implements PagamentoStrategy {

    @Override
    public boolean realizarPagamento(double valor) {
        return true;
    }

}
