package br.ufu.poo2.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import br.ufu.poo2.biblioteca.decorator.CalculaPagamentoDecorator;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_emprestimo", discriminatorType = DiscriminatorType.STRING)
public abstract class Emprestimo implements CalculaPagamentoDecorator {
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

    public float calcularPagamento() {
        return 0;
    }

    public final void rotinaEmprestimo() {
        livro.Emprestar();
        defineDatas();
    }

    public final void rotinaDevolucao() {
        livro.Devolver();
    }

    public abstract void defineDatas();

    public Long getDiasAtraso() {
        Date hoje = new Date();
        if (hoje.after(dataDevolucao)) {
            return (hoje.getTime() - dataDevolucao.getTime()) / (1000 * 60 * 60 * 24);
        }
        return 0L;
    }
}
