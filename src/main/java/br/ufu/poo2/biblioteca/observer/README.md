O padrão Observer é um padrão de design de software onde um objeto, conhecido como sujeito, mantém uma lista de seus dependentes, chamados observadores, e os notifica automaticamente sobre quaisquer mudanças de estado, geralmente chamando um de seus métodos. É um padrão fundamental para a implementação de um sistema orientado a eventos.

No Sistema de Gerenciamento de Biblioteca, podemos aplicar o padrão Observer para notificar os usuários quando um livro se torna disponível. Por exemplo, se um livro está emprestado e um usuário deseja ser notificado quando ele for devolvido e estiver disponível novamente, podemos usar o Observer para isso.

### 1. Definindo a Interface do Sujeito

O sujeito é a entidade que possui as informações do estado. No nosso caso, seria algo relacionado ao status do livro:

```java
public interface LivroObservable {
    void registrarObserver(UsuarioObserver observer);
    void removerObserver(UsuarioObserver observer);
    void notificarObservers();
}
```

### 2. Definindo a Interface do Observer

Os observers são as entidades que querem ser notificadas sobre as mudanças. No nosso caso, seriam os usuários:

```java
public interface UsuarioObserver {
    void atualizar(Livro livro);
}
```

### 3. Implementando o Sujeito Concreto

Implementamos a classe concreta que será o sujeito observado, que neste caso, é um `Livro`:

```java
public class Livro implements LivroObservable {
    private List<UsuarioObserver> observers = new ArrayList<>();
    private boolean disponivel;

    // ... outros atributos e métodos

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        if (disponivel) {
            notificarObservers();
        }
    }

    @Override
    public void registrarObserver(UsuarioObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removerObserver(UsuarioObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notificarObservers() {
        for (UsuarioObserver observer : observers) {
            observer.atualizar(this);
        }
    }
}
```

### 4. Implementando o Observer Concreto

Aqui está um exemplo de como um `Usuario` pode implementar a interface `UsuarioObserver`:

```java
public class Usuario implements UsuarioObserver {
    private String nome;

    // ... outros atributos e métodos

    @Override
    public void atualizar(Livro livro) {
        // Lógica para notificar o usuário de que o livro está disponível
        System.out.println("Olá " + nome + ", o livro " + livro.getTitulo() + " está agora disponível.");
    }
}
```

### 5. Integração com Spring

No Spring, você pode configurar os observadores e o sujeito como beans gerenciados pelo Spring, o que permite que você injete observadores no sujeito de forma declarativa:

```java
@Configuration
public class BibliotecaConfig {

    @Bean
    public Livro livro() {
        // cria a instância do livro
        Livro livro = new Livro();
        // ... configuração adicional
        return livro;
    }

    @Bean
    @Scope("prototype")
    public Usuario usuarioObserver() {
        // cria a instância do usuário
        Usuario usuario = new Usuario();
        // ... configuração adicional
        return usuario;
    }
}
```

No serviço que gerencia os livros, você pode registrar e remover observadores:

```java
@Service
public class ServicoGerenciamentoLivros {

    private final Map<Long, Livro> livros = new HashMap<>();

    // ... outros métodos

    public void registrarInteresseNoLivro(Long livroId, UsuarioObserver usuarioObserver) {
        Livro livro = livros.get(livroId);
        if (livro != null) {
            livro.registrarObserver(usuarioObserver);
        }
    }

    public void cancelarInteresseNoLivro(Long livroId, UsuarioObserver usuarioObserver) {
        Livro livro = livros.get(livroId);
        if (livro != null) {
            livro.removerObserver(usuarioObserver);
        }
    }
}
```

E finalmente, no controlador:

```java
@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final ServicoGerenciamentoLivros servicoGerenciamentoLivros;
    private final ApplicationContext context;

    @Autowired
    public NotificacaoController(ServicoGerenciamentoLivros servicoGerenciamentoLivros,
                                 ApplicationContext context) {
        this.servicoGerenciamentoLivros = servicoGerenciamentoLivros;
        this.context = context;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarInteresse(@RequestParam Long livroId, 
                                                     @RequestParam String nomeUsuario) {
        UsuarioObserver usuario = context.getBean(UsuarioObserver.class);
        usuario.setNome(nomeUsuario);
        servicoGerenciamentoLivros.registrarInteresseNoLivro(livroId, usuario);
        return ResponseEntity.ok("Interesse no livro registrado com sucesso.");
    }

    // ... outros endpoints para cancelar interesse, etc.
}
```

Neste exemplo, `NotificacaoController` usa `ServicoGerenciamentoLivros` para registrar e cancelar o interesse de um usuário em um livro. Quando o livro se torna disponível, o método `setDisponivel` do `Livro` é chamado, que por sua vez notifica todos os usuários registrados através do método `atualizar` do `UsuarioObserver`.

Esta é uma maneira de integrar o padrão Observer em uma aplicação Spring, facilitando a notificação de eventos entre diferentes partes do sistema de forma desacoplada.