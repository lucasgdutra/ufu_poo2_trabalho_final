package br.ufu.poo2.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufu.poo2.biblioteca.model.Multa;

public interface MultaRepository extends JpaRepository<Multa, Long> {

    List<Multa> findByUsuarioId(Long id);

}
