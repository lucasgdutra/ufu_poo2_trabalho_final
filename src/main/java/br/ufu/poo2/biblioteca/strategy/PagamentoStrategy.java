package br.ufu.poo2.biblioteca.strategy;

public interface PagamentoStrategy {
    boolean realizarPagamento(double valor);
}