package br.ufu.poo2.biblioteca.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Professor")
public class EmprestimoProfessor extends Emprestimo {
    private final Long SEIS_MESES = 15552000000L;

    public EmprestimoProfessor() {
        super();
    }

    @Override
    public void defineDatas() {
        setDataEmprestimo(new java.util.Date());
        setDataDevolucao(new java.util.Date(getDataEmprestimo().getTime() + SEIS_MESES));
    }
}
