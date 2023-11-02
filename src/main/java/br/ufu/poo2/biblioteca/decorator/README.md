O padrão Decorator permite adicionar comportamentos a objetos dinamicamente, envolvendo-os em objetos de classes decoradoras que contêm os comportamentos adicionais. No contexto de um Sistema de Gerenciamento de Biblioteca, o Decorator pode ser usado para adicionar dinamicamente multas ou descontos aos empréstimos, dependendo de várias condições (como atraso na devolução, promoções especiais, etc.).

Vamos ver como isso poderia ser implementado no Spring.

### 1. Definindo a interface do empréstimo

Começamos definindo uma interface simples para um empréstimo:

```java
public interface Emprestimo {
    double calcularCusto();
    // ... outros métodos relacionados ao empréstimo
}
```

### 2. Implementando a classe concreta de empréstimo

Em seguida, temos uma implementação concreta da interface `Emprestimo`:

```java
public class EmprestimoConcreto implements Emprestimo {
    private double custoBase;

    public EmprestimoConcreto(double custoBase) {
        this.custoBase = custoBase;
    }

    @Override
    public double calcularCusto() {
        return custoBase;
    }
}
```

### 3. Criando Decorators

Agora, criamos classes decoradoras que adicionam multas ou descontos:

#### Multa por atraso

```java
public class MultaDecorator implements Emprestimo {
    private Emprestimo emprestimoDecorado;
    private double multa;

    public MultaDecorator(Emprestimo emprestimoDecorado, double multa) {
        this.emprestimoDecorado = emprestimoDecorado;
        this.multa = multa;
    }

    @Override
    public double calcularCusto() {
        return emprestimoDecorado.calcularCusto() + multa;
    }
}
```

#### Desconto especial

```java
public class DescontoDecorator implements Emprestimo {
    private Emprestimo emprestimoDecorado;
    private double desconto;

    public DescontoDecorator(Emprestimo emprestimoDecorado, double desconto) {
        this.emprestimoDecorado = emprestimoDecorado;
        this.desconto = desconto;
    }

    @Override
    public double calcularCusto() {
        return emprestimoDecorado.calcularCusto() - desconto;
    }
}
```

### 4. Integrando com Spring

No Spring, você pode configurar esses decorators como beans e injetá-los onde for necessário. Aqui está como você pode configurar um bean decorado dinamicamente:

```java
@Configuration
public class EmprestimoConfig {

    @Bean
    public Emprestimo emprestimo() {
        double custoBase = 10.0; // Valor base do empréstimo
        Emprestimo emprestimo = new EmprestimoConcreto(custoBase);

        // Suponha que queremos aplicar uma multa de 5.0 por atraso
        double multaPorAtraso = 5.0;
        emprestimo = new MultaDecorator(emprestimo, multaPorAtraso);

        // E um desconto especial de 2.0
        double descontoEspecial = 2.0;
        emprestimo = new DescontoDecorator(emprestimo, descontoEspecial);

        return emprestimo;
    }
}
```

E em um serviço:

```java
@Service
public class BibliotecaService {

    private final Emprestimo emprestimo;

    @Autowired
    public BibliotecaService(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public double processarCustoEmprestimo() {
        // O custo será calculado com multas e descontos aplicados
        return emprestimo.calcularCusto();
    }
}
```

Neste exemplo, `BibliotecaService` usa um `Emprestimo` que já tem um decorador de multa e um decorador de desconto aplicados. Você pode criar e combinar decorators dinamicamente, dependendo das regras de negócio do seu sistema (por exemplo, aplicar multas apenas quando há atraso).

Essa abordagem decorativa é poderosa porque ela cumpre o princípio de responsabilidade única e permite a composição de comportamentos em tempo de execução.