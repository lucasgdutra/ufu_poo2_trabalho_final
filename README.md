# Sistema de Gerenciamento de Biblioteca

![Logo](caminho-para-a-imagem-do-logo.png)

## Introdução (Home)

Este sistema foi desenvolvido para gerenciar as operações cotidianas de uma biblioteca, incluindo o empréstimo e a devolução de livros, gerenciamento de usuários e a manutenção do catálogo de livros. A motivação por trás do desenvolvimento deste software surgiu da necessidade de modernizar e automatizar os processos de uma biblioteca tradicional, proporcionando uma melhor experiência tanto para os bibliotecários quanto para os usuários.

![Tela de Livros](caminho-para-a-imagem-da-tela-de-livros.png)

![Tela de Empréstimo](caminho-para-a-imagem-da-tela-de-emprestimo.png)

![Tela de Multas](caminho-para-a-imagem-da-tela-de-multas.png)

![Tela de Usuários](caminho-para-a-imagem-da-tela-de-usuarios.png)

## Padrões de Projeto

### Singleton
Aplicado na classe `BibliotecaService` para garantir uma única instância desta classe durante toda a execução do sistema, proporcionando um ponto global de acesso a este objeto.

```java
public class BibliotecaService {
    private static BibliotecaService instance;
    
    private BibliotecaService() { }
    
    public static synchronized BibliotecaService getInstance() {
        if (instance == null) {
            instance = new BibliotecaService();
        }
        return instance;
    }
}
```

### Factory
Utilizado para criar objetos de `Usuario` e `Emprestimo` de forma encapsulada, permitindo a criação de diferentes tipos de usuários e empréstimos baseado nas regras de negócio.

```java
public abstract class FabricaDeUsuario {
    public abstract Usuario criarUsuario(String nome, String email, String senha);
}
```

### Decorator

### Proxy

### Strategy

### Template

### Facade

## Princípios de Projeto

### SRP (Single Responsibility Principle)
Cada classe no sistema tem uma única responsabilidade. Por exemplo, a classe `UsuarioService` é responsável apenas por operações relacionadas ao gerenciamento de usuários.

```java
@Service
public class UsuarioService {
    public Usuario findById(Long id) { /* ... */ }
    public void saveUsuario(Usuario usuario) { /* ... */ }
}
```

### OCP (Open/Closed Principle)
O sistema foi projetado para ser extensível sem a necessidade de modificar o código existente. Por exemplo, novos tipos de `Emprestimo` podem ser criados e utilizados sem alterar o código existente.

```java
public abstract class Emprestimo {
    public abstract void defineDatas();
}
```

## Framework (Opcional)

### Spring Framework
Optamos pelo Spring Framework devido à sua vasta comunidade, documentação robusta e facilidade de integração com outras tecnologias. Ele é utilizado extensivamente ao longo do projeto, por exemplo, na injeção de dependências.

```java
@Autowired
private UsuarioService usuarioService;
```

- **Vantagens**: Facilita a configuração e a modularidade do projeto, além de proporcionar uma vasta gama de funcionalidades através de seus módulos.
- **Desvantagens**: A curva de aprendizado pode ser íngreme para desenvolvedores menos experientes e a configuração inicial pode ser complexa.

## Conclusão

Durante o desenvolvimento deste projeto, aprendemos a importância de seguir os princípios e padrões de design de software para construir um sistema modular, extensível e de fácil manutenção. Algumas dificuldades encontradas incluíram a configuração inicial do framework e o entendimento de alguns padrões de design mais complexos. No entanto, a aplicação prática desses conceitos resultou em um software robusto e funcional que atende às necessidades de gerenciamento de uma biblioteca.

---

## Autores

 - Guilherme Castilho Machado - 
 - Lucas Gabriel Dutra de Souza - 12121BSI226
 - Victor Ricarte Silva - 
 - Vitória Silva Cardoso - 
