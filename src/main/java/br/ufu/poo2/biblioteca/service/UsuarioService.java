package br.ufu.poo2.biblioteca.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public void criarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public void deleteUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public void saveUsuario(@Valid Usuario usuario) {
        usuarioRepository.save(usuario);
    }

}
