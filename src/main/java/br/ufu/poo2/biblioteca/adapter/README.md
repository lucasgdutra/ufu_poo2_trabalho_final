# Adaptador para integração com sistemas externos de catálogos de livros.

O padrão Adapter é um design pattern estrutural que permite objetos com interfaces incompatíveis colaborarem entre si. Este padrão é frequentemente usado em sistemas onde novos componentes precisam ser integrados e trabalhar junto com componentes existentes no código.

No contexto de um sistema de gerenciamento de biblioteca, suponhamos que queremos integrar um serviço de catálogo de livros externo que tem uma forma diferente de interagir em comparação com o nosso sistema atual. O serviço externo pode ter métodos para buscar livros que não correspondem à interface que nosso sistema espera.

Aqui está como poderíamos definir um adaptador para este cenário:

### 1. Definindo a interface do serviço externo

Suponhamos que o serviço externo de catálogo de livros tenha a seguinte interface:

```java
public interface ExternalBookCatalog {
    ExternalBook findBookByISBN(String isbn);
    List<ExternalBook> listBooksByAuthor(String author);
    // ... outros métodos
}
```

Onde `ExternalBook` é uma classe que tem uma estrutura diferente do nosso modelo `Livro`.

### 2. Criando o Adapter

Criamos um adaptador que implementa a interface do nosso sistema e traduz as chamadas para a interface do serviço externo:

#### AdaptadorCatalogoExterno.java

```java
public class AdaptadorCatalogoExterno implements BookCatalog {
    
    private ExternalBookCatalog externalCatalog;

    public AdaptadorCatalogoExterno(ExternalBookCatalog externalCatalog) {
        this.externalCatalog = externalCatalog;
    }

    @Override
    public Livro findBookByISBN(String isbn) {
        ExternalBook externalBook = externalCatalog.findBookByISBN(isbn);
        return convertToOurBookModel(externalBook);
    }

    @Override
    public List<Livro> listBooksByAuthor(String author) {
        List<ExternalBook> externalBooks = externalCatalog.listBooksByAuthor(author);
        return externalBooks.stream()
                            .map(this::convertToOurBookModel)
                            .collect(Collectors.toList());
    }

    private Livro convertToOurBookModel(ExternalBook externalBook) {
        // Adapta um ExternalBook para o nosso modelo Livro
        Livro livro = new Livro();
        livro.setId(externalBook.getId());
        livro.setTitulo(externalBook.getTitle());
        livro.setAutor(externalBook.getAuthor());
        // ... converter outros campos conforme necessário
        return livro;
    }
}
```

### 3. Integrando o Adapter com o Spring

No Spring, você provavelmente configuraria o adaptador como um bean no contexto da aplicação e o injetaria onde fosse necessário:

```java
@Configuration
public class CatalogConfig {

    @Bean
    public ExternalBookCatalog externalBookCatalog() {
        // Retorna a instância do serviço externo, possivelmente configurando-o aqui
        return new ExternalBookCatalogImpl();
    }

    @Bean
    public BookCatalog bookCatalog(ExternalBookCatalog externalBookCatalog) {
        return new AdaptadorCatalogoExterno(externalBookCatalog);
    }
}
```

E então, em algum serviço que você precisa usar o catálogo:

```java
@Service
public class BibliotecaService {

    private final BookCatalog bookCatalog;

    @Autowired
    public BibliotecaService(BookCatalog bookCatalog) {
        this.bookCatalog = bookCatalog;
    }

    public void someMethod() {
        // Você pode chamar bookCatalog.findBookByISBN(...)
        // E o Spring cuidará de usar o adaptador para fazer a chamada correta ao serviço externo
    }
}
```

Com essa configuração, quando você chama o método `findBookByISBN` do `bookCatalog`, o Spring usará o `AdaptadorCatalogoExterno` para invocar o método correspondente no serviço de catálogo de livros externo, e converterá o resultado de volta para o modelo de dados esperado pelo sistema de gerenciamento da biblioteca.