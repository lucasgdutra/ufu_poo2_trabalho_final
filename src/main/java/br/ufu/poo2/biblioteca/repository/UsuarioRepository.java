package br.ufu.poo2.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufu.poo2.biblioteca.model.Usuario;
import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByEmail(String email);

}