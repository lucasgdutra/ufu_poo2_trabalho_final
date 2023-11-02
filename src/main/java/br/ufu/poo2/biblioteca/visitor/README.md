O padrão Visitor permite adicionar novas operações a classes de objetos sem modificá-las. É uma maneira de separar um algoritmo da estrutura de um objeto. No contexto de um Sistema de Gerenciamento de Biblioteca, podemos usar o Visitor para implementar relatórios variados sobre empréstimos, reservas e multas sem alterar as classes que representam esses conceitos.

### 1. Definindo a Interface Visitor

Primeiro, definimos uma interface `Visitor` que declara um método de visita para cada tipo de elemento que pode ser "visitado":

```java
public interface RelatorioVisitor {
    void visitar(Livro livro);
    void visitar(Usuario usuario);
    void visitar(Emprestimo emprestimo);
    // Outros métodos para diferentes tipos de visitáveis, como Reserva, Multa, etc.
}
```

### 2. Elementos Visitáveis

As classes de elementos que podem ser visitadas precisam ter um método que aceita o visitor:

```java
public class Livro {
    // ...
    public void aceitar(RelatorioVisitor visitor) {
        visitor.visitar(this);
    }
    // ...
}

public class Usuario {
    // ...
    public void aceitar(RelatorioVisitor visitor) {
        visitor.visitar(this);
    }
    // ...
}

public class Emprestimo {
    // ...
    public void aceitar(RelatorioVisitor visitor) {
        visitor.visitar(this);
    }
    // ...
}
```

### 3. Implementando Visitors Concretos

A seguir, criamos implementações concretas da interface `Visitor` para diferentes tipos de relatórios:

```java
public class RelatorioEmprestimosVisitor implements RelatorioVisitor {
    private String relatorio = "";

    @Override
    public void visitar(Livro livro) {
        relatorio += "Relatório do Livro: " + livro.getTitulo() + "\n";
    }

    @Override
    public void visitar(Usuario usuario) {
        relatorio += "Relatório do Usuário: " + usuario.getNome() + "\n";
    }

    @Override
    public void visitar(Emprestimo emprestimo) {
        relatorio += "Relatório de Empréstimo: " + emprestimo.getInfo() + "\n";
    }

    public String getRelatorio() {
        return relatorio;
    }
}
```

### 4. Integração com Spring

No Spring, podemos configurar o Visitor como um bean e injetá-lo onde for necessário:

```java
@Configuration
public class RelatorioConfig {

    @Bean
    public RelatorioVisitor relatorioEmprestimosVisitor() {
        return new RelatorioEmprestimosVisitor();
    }
}
```

### 5. Usando o Visitor na Aplicação

Em um serviço de relatórios, podemos passar um visitor apropriado para os elementos que desejamos reportar:

```java
@Service
public class ServicoRelatorios {

    private final RelatorioVisitor relatorioVisitor;

    @Autowired
    public ServicoRelatorios(RelatorioVisitor relatorioVisitor) {
        this.relatorioVisitor = relatorioVisitor;
    }

    public String gerarRelatorioEmprestimos(List<Emprestimo> emprestimos) {
        for (Emprestimo emprestimo : emprestimos) {
            emprestimo.aceitar(relatorioVisitor);
        }
        return ((RelatorioEmprestimosVisitor) relatorioVisitor).getRelatorio();
    }
}
```

Em um controlador, poderíamos ter um endpoint que dispara a geração do relatório:

```java
@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {

    private final ServicoRelatorios servicoRelatorios;

    @Autowired
    public RelatoriosController(ServicoRelatorios servicoRelatorios) {
        this.servicoRelatorios = servicoRelatorios;
    }

    @GetMapping("/emprestimos")
    public ResponseEntity<String> gerarRelatorioEmprestimos() {
        List<Emprestimo> emprestimos = // lógica para obter todos os empréstimos
        String relatorio = servicoRelatorios.gerarRelatorioEmprestimos(emprestimos);
        return ResponseEntity.ok(relatorio);
    }
}
```

### Como o Visitor Funciona Aqui

Quando o método `gerarRelatorioEmprestimos` é chamado, ele percorre todos os empréstimos e invoca o método `aceitar` em cada um deles, passando o `RelatorioVisitor` como argumento. Cada empréstimo, por sua vez, chama o método `visitar` de volta no `RelatorioVisitor`, que executa a operação de relatório apropriada. O resultado é um relatório agregado que é devolvido ao controlador e pode ser apresentado ao usuário.

O uso do padrão Visitor permite que novos relatórios sejam adicionados facilmente, simplesmente implementando novos visitors, sem a necessidade de alterar as classes de elementos visitados (`Livro`, `Usuario`, `Emprestimo`, etc.). Isso mantém as operações de relatório desacopladas das classes de domínio, promovendo uma arquitetura mais limpa e flexível.