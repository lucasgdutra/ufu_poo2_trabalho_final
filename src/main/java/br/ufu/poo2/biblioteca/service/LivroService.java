package br.ufu.poo2.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufu.poo2.biblioteca.model.Livro;
import br.ufu.poo2.biblioteca.repository.LivroRepository;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;

    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public void saveLivro(Livro livro) {
        livroRepository.save(livro);
    }

    public Livro findById(Long id) {
        return livroRepository.findById(id).get();
    }

    public void deleteLivro(Livro livro) {
        livroRepository.delete(livro);
    }

}
