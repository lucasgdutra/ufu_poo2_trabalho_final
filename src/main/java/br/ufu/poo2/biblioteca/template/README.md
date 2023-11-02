O padrão Template Method é um padrão de design comportamental que define o esqueleto de um algoritmo em uma operação, adiando alguns passos para subclasses. Ele permite que subclasses redefinam certas etapas de um algoritmo sem alterar a estrutura do próprio algoritmo.

No Sistema de Gerenciamento de Biblioteca, podemos ter um processo de empréstimo de livros que requer etapas padrão, mas com algumas variações dependendo do tipo de usuário (por exemplo, estudante ou professor) ou do tipo de livro (por exemplo, referência ou ficção).

### 1. Definindo a Classe Base com o Método Template

Vamos definir uma classe base `Emprestimo` com o método template `realizarEmprestimo`:

```java
public abstract class Emprestimo {

    // Este é o método template
    public final void realizarEmprestimo(Usuario usuario, Livro livro) {
        verificarDisponibilidade(livro);
        aplicarRegrasDeEmprestimo(usuario, livro);
        registrarEmprestimo(usuario, livro);
        enviarNotificacaoEmprestimo(usuario, livro);
        // Outras etapas comuns...
    }

    protected abstract void verificarDisponibilidade(Livro livro);

    protected abstract void aplicarRegrasDeEmprestimo(Usuario usuario, Livro livro);

    private void registrarEmprestimo(Usuario usuario, Livro livro) {
        // Lógica de registro comum
    }

    private void enviarNotificacaoEmprestimo(Usuario usuario, Livro livro) {
        // Lógica de notificação comum
    }

    // Outros métodos concretos e abstratos...
}
```

### 2. Implementando Subclasses Específicas

Subclasses específicas implementarão as nuances dos métodos abstratos:

```java
public class EmprestimoEstudante extends Emprestimo {

    @Override
    protected void verificarDisponibilidade(Livro livro) {
        // Verificar disponibilidade específica para estudante
    }

    @Override
    protected void aplicarRegrasDeEmprestimo(Usuario usuario, Livro livro) {
        // Regras de empréstimo específicas para estudante
    }
}

public class EmprestimoProfessor extends Emprestimo {

    @Override
    protected void verificarDisponibilidade(Livro livro) {
        // Professores podem ter acesso a livros mesmo que estes estejam reservados
    }

    @Override
    protected void aplicarRegrasDeEmprestimo(Usuario usuario, Livro livro) {
        // Regras de empréstimo específicas para professor, como prazos mais longos
    }
}
```

### 3. Integração com Spring

No Spring, podemos configurar essas classes como beans gerenciados pelo Spring e utilizá-los de acordo com o tipo de usuário:

```java
@Configuration
public class EmprestimoConfig {

    @Bean
    @Scope("prototype")
    public Emprestimo emprestimoEstudante() {
        return new EmprestimoEstudante();
    }

    @Bean
    @Scope("prototype")
    public Emprestimo emprestimoProfessor() {
        return new EmprestimoProfessor();
    }
}
```

### 4. Usando o Template Method na Aplicação

Em um serviço de empréstimos, podemos decidir qual objeto `Emprestimo` usar baseado no tipo de usuário e invocar o método template:

```java
@Service
public class ServicoDeEmprestimos {

    private final ApplicationContext context;

    @Autowired
    public ServicoDeEmprestimos(ApplicationContext context) {
        this.context = context;
    }

    public void realizarEmprestimo(Usuario usuario, Livro livro) {
        Emprestimo emprestimo;
        if (usuario instanceof UsuarioEstudante) {
            emprestimo = context.getBean(EmprestimoEstudante.class);
        } else if (usuario instanceof UsuarioProfessor) {
            emprestimo = context.getBean(EmprestimoProfessor.class);
        } else {
            throw new IllegalArgumentException("Tipo de usuário não suportado para empréstimo.");
        }
        
        emprestimo.realizarEmprestimo(usuario, livro);
    }
}
```

### Como Funciona o Template Method Aqui

Quando o método `realizarEmprestimo` é chamado no serviço `ServicoDeEmprestimos`, ele delega para o objeto `Emprestimo` apropriado, que executa o algoritmo de empréstimo. As etapas comuns de empréstimo são realizadas pela classe base, enquanto as etapas específicas, que podem variar conforme o tipo de usuário ou livro, são implementadas pelas subclasses.

Isso permite que o processo de empréstimo seja estendido facilmente para diferentes categorias de usuários ou livros, mantendo o mesmo fluxo de operações, garantindo a consistência e a reutilização do código.