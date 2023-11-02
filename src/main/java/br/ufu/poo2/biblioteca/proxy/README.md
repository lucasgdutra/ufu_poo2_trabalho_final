O padrão Proxy fornece um substituto ou marcador de posição para outro objeto para controlar o acesso a ele. Isso é útil quando você quer adicionar uma camada de segurança ou algum outro tipo de controle de acesso quando um objeto é acessado.

No contexto do nosso Sistema de Gerenciamento de Biblioteca, podemos usar um Proxy para controlar o acesso às informações sensíveis dos usuários. Por exemplo, podemos querer verificar se um usuário tem permissões adequadas antes de permitir que ele visualize ou modifique informações de usuário sensíveis.

### 1. Definindo a Interface

Primeiro, definimos a interface que descreve os métodos que serão acessados através do proxy:

```java
public interface InformacoesUsuario {
    Usuario getUsuarioById(Long id);
    void atualizarUsuario(Usuario usuario);
}
```

### 2. Implementando a Classe Concreta

A seguir, temos a implementação concreta desta interface, que seria o objeto real sendo acessado:

```java
public class ServicoInformacoesUsuario implements InformacoesUsuario {

    // ... aqui iria a lógica de acesso ao repositório de dados, por exemplo

    @Override
    public Usuario getUsuarioById(Long id) {
        // acesso ao banco de dados para recuperar o usuário
        return new Usuario();
    }

    @Override
    public void atualizarUsuario(Usuario usuario) {
        // atualizar informações do usuário no banco de dados
    }
}
```

### 3. Criando o Proxy

Agora, criamos o proxy que implementa a mesma interface e adiciona a camada de controle de acesso:

```java
public class ProxyInformacoesUsuario implements InformacoesUsuario {

    private ServicoInformacoesUsuario servicoInformacoesUsuario;
    private ServicoAutenticacao servicoAutenticacao;

    public ProxyInformacoesUsuario(ServicoInformacoesUsuario servicoInformacoesUsuario, 
                                   ServicoAutenticacao servicoAutenticacao) {
        this.servicoInformacoesUsuario = servicoInformacoesUsuario;
        this.servicoAutenticacao = servicoAutenticacao;
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        if (servicoAutenticacao.temPermissaoAcesso()) {
            return servicoInformacoesUsuario.getUsuarioById(id);
        } else {
            throw new SecurityException("Acesso negado.");
        }
    }

    @Override
    public void atualizarUsuario(Usuario usuario) {
        if (servicoAutenticacao.temPermissaoAcesso()) {
            servicoInformacoesUsuario.atualizarUsuario(usuario);
        } else {
            throw new SecurityException("Acesso negado.");
        }
    }
}
```

### 4. Integração com Spring

No Spring, podemos configurar o proxy como um bean e injetá-lo onde for necessário:

```java
@Configuration
public class ProxyConfig {

    @Bean
    public InformacoesUsuario informacoesUsuarioProxy(ServicoInformacoesUsuario servicoInformacoesUsuario, 
                                                      ServicoAutenticacao servicoAutenticacao) {
        return new ProxyInformacoesUsuario(servicoInformacoesUsuario, servicoAutenticacao);
    }
}
```

E o serviço de autenticação:

```java
@Service
public class ServicoAutenticacao {
    public boolean temPermissaoAcesso() {
        // Aqui iria a lógica para verificar se o usuário atual tem permissão
        return true; // ou false, dependendo da verificação de permissões
    }
}
```

### 5. Usando o Proxy na Aplicação

Agora, quando um serviço ou controlador precisa acessar informações de usuário, ele usará o `ProxyInformacoesUsuario` que vai aplicar as checagens de permissão antes de realizar a operação:

```java
@Service
public class UsuarioService {

    private final InformacoesUsuario informacoesUsuario;

    @Autowired
    public UsuarioService(InformacoesUsuario informacoesUsuario) {
        this.informacoesUsuario = informacoesUsuario;
    }

    public Usuario getUsuario(Long id) {
        return informacoesUsuario.getUsuarioById(id);
    }

    public void updateUsuario(Usuario usuario) {
        informacoesUsuario.atualizarUsuario(usuario);
    }
}
```

Com essa configuração, qualquer tentativa de acessar ou modificar as informações do usuário passará pela lógica de controle de acesso do `ProxyInformacoesUsuario`, que delegará a operação para o `ServicoInformacoesUsuario` se a verificação de permissões for bem-sucedida. Se a verificação falhar, uma exceção será lançada, impedindo o acesso ou a modificação não autorizada das informações do usuário.