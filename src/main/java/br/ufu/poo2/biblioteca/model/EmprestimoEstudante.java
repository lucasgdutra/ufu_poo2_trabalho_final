package br.ufu.poo2.biblioteca.model;

import br.ufu.poo2.biblioteca.decorator.DescontoDecorator;
import br.ufu.poo2.biblioteca.strategy.PagamentoStrategy;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Estudante")
public class EmprestimoEstudante extends Emprestimo {

    public EmprestimoEstudante() {
        super();
    }

    @Override
    public float calcularPagamento(int diasAtraso) {
        return super.calcularPagamento(diasAtraso);
    }

    public void setPagamentoStrategy(PagamentoStrategy pagamentoStrategy) {
        this.pagamentoStrategy = new DescontoDecorator(pagamentoStrategy);
    }
}
