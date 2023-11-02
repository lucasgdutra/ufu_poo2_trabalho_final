O padrão Strategy permite definir uma família de algoritmos, encapsular cada um deles e torná-los intercambiáveis. O padrão Strategy permite que o algoritmo varie independentemente dos clientes que o usam. No contexto do nosso Sistema de Gerenciamento de Biblioteca, podemos aplicar o padrão Strategy para notificar usuários sobre devoluções de livros de diferentes maneiras, como por e-mail, SMS ou notificações push.

### 1. Definindo a Estratégia de Notificação

Primeiro, definimos a interface da estratégia que todas as estratégias concretas de notificação irão implementar:

```java
public interface EstrategiaNotificacao {
    void enviarNotificacao(Usuario usuario, String mensagem);
}
```

### 2. Implementando Estratégias Concretas

A seguir, criamos implementações concretas para diferentes tipos de notificações:

```java
public class NotificacaoEmail implements EstrategiaNotificacao {
    @Override
    public void enviarNotificacao(Usuario usuario, String mensagem) {
        // Lógica para enviar e-mail
    }
}

public class NotificacaoSMS implements EstrategiaNotificacao {
    @Override
    public void enviarNotificacao(Usuario usuario, String mensagem) {
        // Lógica para enviar SMS
    }
}

public class NotificacaoPush implements EstrategiaNotificacao {
    @Override
    public void enviarNotificacao(Usuario usuario, String mensagem) {
        // Lógica para enviar notificação push
    }
}
```

### 3. Integrando com Spring

No Spring, podemos configurar cada estratégia como um bean e injetá-los onde necessário, potencialmente usando qualificação para selecionar a estratégia apropriada em tempo de execução:

```java
@Configuration
public class NotificacaoConfig {

    @Bean
    public EstrategiaNotificacao notificacaoEmail() {
        return new NotificacaoEmail();
    }

    @Bean
    public EstrategiaNotificacao notificacaoSMS() {
        return new NotificacaoSMS();
    }

    @Bean
    public EstrategiaNotificacao notificacaoPush() {
        return new NotificacaoPush();
    }
}
```

### 4. Usando a Estratégia na Aplicação

Em seguida, podemos criar um serviço que usa uma estratégia de notificação. Em tempo de execução, podemos decidir qual estratégia usar, dependendo das preferências do usuário ou de outras condições:

```java
@Service
public class ServicoNotificacao {

    private EstrategiaNotificacao estrategiaNotificacao;

    // Injetar todas as estratégias disponíveis
    private final Map<String, EstrategiaNotificacao> estrategiasNotificacao;

    @Autowired
    public ServicoNotificacao(Map<String, EstrategiaNotificacao> estrategiasNotificacao) {
        this.estrategiasNotificacao = estrategiasNotificacao;
    }

    public void setEstrategiaNotificacao(EstrategiaNotificacao estrategiaNotificacao) {
        this.estrategiaNotificacao = estrategiaNotificacao;
    }

    public void notificarUsuario(Usuario usuario, String mensagem) {
        estrategiaNotificacao.enviarNotificacao(usuario, mensagem);
    }

    public void enviarNotificacaoDeDevolucao(Usuario usuario) {
        // Definir a estratégia com base na preferência do usuário
        // Por exemplo, o usuário pode definir suas preferências em algum lugar do sistema
        String tipoNotificacaoPreferencial = usuario.getPreferenciaNotificacao();
        setEstrategiaNotificacao(estrategiasNotificacao.get(tipoNotificacaoPreferencial));

        String mensagem = "Seu livro emprestado agora está disponível para devolução.";
        notificarUsuario(usuario, mensagem);
    }
}
```

Com essa configuração, o `ServicoNotificacao` pode alterar dinamicamente a estratégia de notificação com base na preferência do usuário ou em qualquer outra lógica de negócios. Isso permite que a forma como os usuários são notificados seja facilmente estendida ou modificada sem alterar o código que usa o serviço de notificação.

### 5. Exemplo de Uso em um Controlador

Por fim, poderíamos usar nosso `ServicoNotificacao` em um controlador para enviar notificações de devolução:

```java
@RestController
@RequestMapping("/devolucoes")
public class DevolucaoController {

    private final ServicoNotificacao servicoNotificacao;

    @Autowired
    public DevolucaoController(ServicoNotificacao servicoNotificacao) {
        this.servicoNotificacao = servicoNotificacao;
    }

    @PostMapping("/notificar/{usuarioId}")
    public ResponseEntity<String> notificarDevolucao(@PathVariable Long usuarioId) {
        Usuario usuario = // lógica para obter o usuário pelo ID
        servicoNotificacao.enviarNotificacaoDeDevolucao(usuario);
        return ResponseEntity.ok("Usuário notificado com sucesso.");
    }
}
```

Este exemplo ilustra como o padrão Strategy, combinado com o Spring, oferece uma maneira elegante de separar a lógica de seleção de estratégia da lógica de execução da estratégia, resultando em um design de software mais flexível e fácil de manter.