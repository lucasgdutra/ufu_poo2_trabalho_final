O padrão Abstract Factory é utilizado para fornecer uma interface para criar famílias de objetos relacionados ou dependentes sem especificar suas classes concretas. No contexto do nosso Sistema de Gerenciamento de Biblioteca, poderíamos ter uma fábrica abstrata `FabricaDeUsuarios` que cria diferentes tipos de usuários, como `UsuarioEstudante` ou `UsuarioProfessor`.

### 1. Definindo a Interface da Fábrica

Primeiro, definimos a interface da fábrica abstrata:

```java
public interface FabricaDeUsuarios {
    Usuario criarUsuario();
}
```

### 2. Implementando as Fábricas Concretas

Criamos implementações concretas da fábrica para diferentes tipos de usuários:

```java
public class FabricaDeEstudantes implements FabricaDeUsuarios {
    @Override
    public Usuario criarUsuario() {
        return new UsuarioEstudante();
    }
}

public class FabricaDeProfessores implements FabricaDeUsuarios {
    @Override
    public Usuario criarUsuario() {
        return new UsuarioProfessor();
    }
}
```

### 3. Definindo as Classes de Usuário

Definimos as classes concretas de usuário que a fábrica vai criar:

```java
public class UsuarioEstudante extends Usuario {
    // Implementação específica para estudante
}

public class UsuarioProfessor extends Usuario {
    // Implementação específica para professor
}
```

### 4. Integrando com Spring

No Spring, podemos configurar as fábricas como beans no contexto da aplicação e injetar a fábrica apropriada onde for necessário.

```java
@Configuration
public class UsuarioConfig {

    @Bean
    public FabricaDeUsuarios fabricaDeEstudantes() {
        return new FabricaDeEstudantes();
    }

    @Bean
    public FabricaDeUsuarios fabricaDeProfessores() {
        return new FabricaDeProfessores();
    }
}
```

Então, injetamos a fábrica adequada no serviço que vai criar os usuários:

```java
@Service
public class ServicoDeCadastroDeUsuarios {

    private FabricaDeUsuarios fabricaDeUsuarios;

    // Este método poderia ser chamado com uma flag ou parâmetro para escolher o tipo de usuário
    public void setFabricaDeUsuarios(FabricaDeUsuarios fabricaDeUsuarios) {
        this.fabricaDeUsuarios = fabricaDeUsuarios;
    }

    public Usuario registrarNovoUsuario(String tipo) {
        // Define a fábrica com base no tipo de usuário
        if ("estudante".equals(tipo)) {
            setFabricaDeUsuarios(new FabricaDeEstudantes());
        } else if ("professor".equals(tipo)) {
            setFabricaDeUsuarios(new FabricaDeProfessores());
        }

        // Usa a fábrica para criar o tipo de usuário correspondente
        return fabricaDeUsuarios.criarUsuario();
    }
}
```

### 5. Uso do Serviço

Quando precisarmos registrar um novo usuário, simplesmente chamamos o método `registrarNovoUsuario` com o tipo de usuário apropriado:

```java
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final ServicoDeCadastroDeUsuarios servicoDeCadastroDeUsuarios;

    @Autowired
    public UsuarioController(ServicoDeCadastroDeUsuarios servicoDeCadastroDeUsuarios) {
        this.servicoDeCadastroDeUsuarios = servicoDeCadastroDeUsuarios;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestParam String tipo) {
        Usuario novoUsuario = servicoDeCadastroDeUsuarios.registrarNovoUsuario(tipo);
        return ResponseEntity.ok(novoUsuario);
    }

    // ... outros endpoints
}
```

Neste exemplo, o `UsuarioController` usa o `ServicoDeCadastroDeUsuarios` para registrar novos usuários. O serviço usa a fábrica abstrata `FabricaDeUsuarios` para criar uma instância do tipo correto de usuário, dependendo do tipo passado para o método `registrarNovoUsuario`.

Essa abordagem mantém o código de criação de usuários flexível e facilmente expansível para outros tipos de usuários, seguindo o princípio Open/Closed do SOLID.