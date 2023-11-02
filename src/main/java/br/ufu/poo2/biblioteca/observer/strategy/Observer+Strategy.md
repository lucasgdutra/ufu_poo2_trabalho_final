Em um Sistema de Gerenciamento de Biblioteca, o padrão Observer pode ser usado para rastrear eventos, como quando um livro se torna disponível, e o padrão Strategy pode ser utilizado para definir como os usuários são notificados sobre esses eventos. Vamos explorar como esses dois padrões podem ser combinados.

### 1. Observer para Eventos de Disponibilidade de Livros

Vamos começar configurando o Observer para que os usuários possam se inscrever para receber notificações quando um livro se torna disponível:

```java
public interface DisponibilidadeLivroObserver {
    void notificarDisponibilidade(Livro livro);
}

public class Livro {
    private List<DisponibilidadeLivroObserver> observers = new ArrayList<>();
    private boolean disponivel;

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        if (disponivel) {
            notificarObserversDisponibilidade();
        }
    }

    private void notificarObserversDisponibilidade() {
        for (DisponibilidadeLivroObserver observer : observers) {
            observer.notificarDisponibilidade(this);
        }
    }

    public void registrarObserver(DisponibilidadeLivroObserver observer) {
        observers.add(observer);
    }

    public void removerObserver(DisponibilidadeLivroObserver observer) {
        observers.remove(observer);
    }
}
```

### 2. Strategy para Métodos de Notificação

Agora, definimos uma interface de estratégia para diferentes métodos de notificação:

```java
public interface EstrategiaNotificacao {
    void enviarNotificacao(Usuario usuario, String mensagem);
}
```

E suas implementações concretas:

```java
public class NotificacaoEmail implements EstrategiaNotificacao {
    // ...
}

public class NotificacaoSMS implements EstrategiaNotificacao {
    // ...
}

public class NotificacaoPush implements EstrategiaNotificacao {
    // ...
}
```

### 3. Integrando Observer com Strategy

Agora, vamos integrar os dois padrões. Criaremos um serviço que será um Observer e usará uma estratégia de notificação para informar aos usuários:

```java
public class ServicoNotificacaoUsuario implements DisponibilidadeLivroObserver {
    
    private EstrategiaNotificacao estrategiaNotificacao;

    // Permite mudar a estratégia de notificação em tempo de execução
    public void setEstrategiaNotificacao(EstrategiaNotificacao estrategiaNotificacao) {
        this.estrategiaNotificacao = estrategiaNotificacao;
    }

    @Override
    public void notificarDisponibilidade(Livro livro) {
        // Aqui podemos recuperar os usuários interessados no livro e suas preferências de notificação
        List<Usuario> usuariosInteressados = // lógica para recuperar os usuários

        for (Usuario usuario : usuariosInteressados) {
            // A estratégia é escolhida com base na preferência do usuário
            setEstrategiaNotificacao(usuario.getEstrategiaNotificacaoPreferida());

            String mensagem = "Olá " + usuario.getNome() + ", o livro '" + livro.getTitulo() + "' está agora disponível.";
            estrategiaNotificacao.enviarNotificacao(usuario, mensagem);
        }
    }
}
```

### 4. Configuração com Spring

No contexto do Spring, configuramos os beans e os injetamos onde são necessários:

```java
@Configuration
public class NotificacaoConfig {

    @Bean
    public EstrategiaNotificacao notificacaoEmail() {
        return new NotificacaoEmail();
    }

    // ... outras estratégias ...

    @Bean
    public ServicoNotificacaoUsuario servicoNotificacaoUsuario() {
        // O serviço começa com uma estratégia padrão, que pode ser alterada dinamicamente
        ServicoNotificacaoUsuario servico = new ServicoNotificacaoUsuario();
        servico.setEstrategiaNotificacao(notificacaoEmail());
        return servico;
    }
}
```

E em algum lugar no sistema, quando um livro é devolvido e sua disponibilidade é alterada:

```java
public class BibliotecaService {

    private final Livro livro;
    private final ServicoNotificacaoUsuario servicoNotificacaoUsuario;

    @Autowired
    public BibliotecaService(Livro livro, ServicoNotificacaoUsuario servicoNotificacaoUsuario) {
        this.livro = livro;
        this.servicoNotificacaoUsuario = servicoNotificacaoUsuario;
    }

    public void devolverLivro(Long livroId) {
        // ... lógica para devolver o livro ...
        livro.setDisponivel(true);

        // Registrar o serviço de notificação como um observador do livro
        livro.registrarObserver(servicoNotificacaoUsuario);
    }
}
```

### Como Eles Trabalham Juntos

Quando um livro se torna disponível (`livro.setDisponivel(true)`), o `Livro` notifica todos os seus observers (`servicoNotificacaoUsuario`). O `servicoNotificacaoUsuario` então usa a estratégia de notificação adequada (por exemplo, `NotificacaoEmail`, `NotificacaoSMS`, `NotificacaoPush`) para enviar a notificação aos usuários interessados.

Essa combinação dos padrões Observer e Strategy permite que o sistema notifique os usuários de forma flexível e configurável, escolhendo o método de notificação com base nas preferências do usuário ou outras lógicas de negócios, enquanto mantém o rastreamento de eventos de disponibilidade de livros desacoplado da lógica de notificação.