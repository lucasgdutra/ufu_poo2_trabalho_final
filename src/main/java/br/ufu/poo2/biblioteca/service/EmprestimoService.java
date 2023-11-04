package br.ufu.poo2.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufu.poo2.biblioteca.model.Emprestimo;
import br.ufu.poo2.biblioteca.repository.EmprestimoRepository;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public Emprestimo findById(Long id) {
        return emprestimoRepository.findById(id).orElse(null);
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    public void deleteEmprestimo(Emprestimo emprestimo) {
        emprestimoRepository.delete(emprestimo);
    }

    public void saveEmprestimo(Emprestimo emprestimo) {
        emprestimoRepository.save(emprestimo);
    }
}
