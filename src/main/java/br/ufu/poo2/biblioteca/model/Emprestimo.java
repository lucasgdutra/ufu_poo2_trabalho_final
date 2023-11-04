package br.ufu.poo2.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import br.ufu.poo2.biblioteca.strategy.PagamentoStrategy;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_emprestimo", discriminatorType = DiscriminatorType.STRING)
public abstract class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Temporal(TemporalType.DATE)
    private Date dataEmprestimo;

    @Temporal(TemporalType.DATE)
    private Date dataDevolucao;

    @Transient
    protected PagamentoStrategy pagamentoStrategy;

    public float calcularPagamento(int diasAtraso) {
        return pagamentoStrategy.calcularPagamento(diasAtraso);
    }
}
