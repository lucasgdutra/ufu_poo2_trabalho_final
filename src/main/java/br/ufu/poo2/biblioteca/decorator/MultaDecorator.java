package br.ufu.poo2.biblioteca.decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.ufu.poo2.biblioteca.model.Emprestimo;

public class MultaDecorator implements CalculaPagamentoDecorator {
    private Emprestimo emprestimo;
    private int diasAtraso;

    public MultaDecorator(Emprestimo emprestimo, int diasAtraso) {
        this.emprestimo = emprestimo;
        this.diasAtraso = diasAtraso;
    }

    public float calcularPagamento() {
        float valorBase = emprestimo.calcularPagamento();
        float novoValor = valorBase + 1.5f + (0.05f * diasAtraso);
        float rounded = new BigDecimal(novoValor).setScale(2, RoundingMode.HALF_EVEN).floatValue();
        return rounded;

    }

}
