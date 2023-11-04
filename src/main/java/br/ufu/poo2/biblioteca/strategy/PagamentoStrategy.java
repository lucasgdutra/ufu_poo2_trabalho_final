package br.ufu.poo2.biblioteca.strategy;

public interface PagamentoStrategy {
    float calcularPagamento(int diasAtraso);
}