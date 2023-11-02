Há várias APIs conhecidas que você poderia usar para buscar informações sobre livros através do ISBN. Uma das mais famosas é a API do Google Books. A API do Google Books permite que você pesquise livros e obtenha dados detalhados de cada um, como o título, autor, descrição e outros metadados relacionados ao livro.

Aqui está um exemplo simplificado de como um adaptador para a API do Google Books poderia ser implementado em um projeto Spring:

### 1. A interface do nosso catálogo de livros

```java
public interface BookCatalog {
    Livro findBookByISBN(String isbn);
    // ... outros métodos conforme necessário
}
```

### 2. Classe para representar o livro

```java
public class Livro {
    private String isbn;
    private String titulo;
    private String autor;
    // ... outros atributos, getters e setters
}
```

### 3. O Adaptador

```java
public class GoogleBooksAdapter implements BookCatalog {
    
    private final GoogleBooksApi googleBooksApi;

    public GoogleBooksAdapter(GoogleBooksApi googleBooksApi) {
        this.googleBooksApi = googleBooksApi;
    }

    @Override
    public Livro findBookByISBN(String isbn) {
        // Chama a API do Google Books e converte a resposta para o nosso modelo de Livro
        GoogleBookResponse response = googleBooksApi.searchByISBN(isbn);
        return convertToBook(response);
    }

    private Livro convertToBook(GoogleBookResponse response) {
        // Converte o objeto de resposta da API do Google Books para o nosso modelo Livro
        Livro livro = new Livro();
        livro.setIsbn(response.getVolumeInfo().getIndustryIdentifiers().get(0).getIdentifier());
        livro.setTitulo(response.getVolumeInfo().getTitle());
        livro.setAutor(response.getVolumeInfo().getAuthors().get(0));
        // ... converter outros campos conforme necessário
        return livro;
    }
}
```

### 4. Integração com Spring

```java
@Configuration
public class GoogleBooksConfig {

    @Value("${google.books.api.key}")
    private String apiKey;

    @Bean
    public GoogleBooksApi googleBooksApi() {
        // Retorna a instância da API do Google Books configurada com a chave de API
        return new GoogleBooksApiImpl(apiKey);
    }

    @Bean
    public BookCatalog bookCatalog(GoogleBooksApi googleBooksApi) {
        return new GoogleBooksAdapter(googleBooksApi);
    }
}
```

No exemplo acima, o `GoogleBooksApi` seria uma interface representando o cliente da API do Google Books, que seria implementada por uma classe concreta (não mostrada aqui) que faz as chamadas de rede reais. O `GoogleBooksAdapter` é um adaptador que implementa a nossa interface `BookCatalog` e usa o `GoogleBooksApi` para buscar livros. A configuração do Spring injeta a dependência do cliente da API do Google Books no adaptador.

Para usar a API do Google Books, você precisaria se registrar para obter uma chave de API e estar ciente das limitações de uso, como quotas e limites de requisições. As classes de resposta, como `GoogleBookResponse`, representariam o formato dos dados retornados pela API do Google Books, que você teria que mapear para o seu modelo de domínio.

Lembre-se de que, para integrar com APIs externas em um ambiente de produção, você também precisaria lidar com autenticação, tratamento de erros, e possivelmente implementar caching ou outras otimizações para melhorar o desempenho e a robustez da integração.