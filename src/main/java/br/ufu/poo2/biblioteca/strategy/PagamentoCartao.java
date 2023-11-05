package br.ufu.poo2.biblioteca.strategy;

public class PagamentoCartao implements PagamentoStrategy {

    @Override
    public boolean realizarPagamento(double valor) {
        return true;
    }

}
