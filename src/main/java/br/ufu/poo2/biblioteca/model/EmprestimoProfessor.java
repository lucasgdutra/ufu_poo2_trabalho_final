package br.ufu.poo2.biblioteca.model;

import br.ufu.poo2.biblioteca.decorator.DescontoDecorator;
import br.ufu.poo2.biblioteca.strategy.PagamentoStrategy;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Professor")
public class EmprestimoProfessor extends Emprestimo {
    public EmprestimoProfessor() {
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
