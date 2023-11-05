package br.ufu.poo2.biblioteca.model;

import java.util.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Estudante")
public class EmprestimoEstudante extends Emprestimo {
    private final Long SETE_DIAS = 604800000L;

    public EmprestimoEstudante() {
        super();
    }

    @Override
    public void defineDatas() {
        setDataEmprestimo(new Date());
        setDataDevolucao(new Date(getDataEmprestimo().getTime() + SETE_DIAS));
    }
}
