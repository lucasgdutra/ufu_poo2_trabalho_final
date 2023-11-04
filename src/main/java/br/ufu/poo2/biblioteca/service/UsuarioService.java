package br.ufu.poo2.biblioteca.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public void deleteUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public void saveUsuario(@Valid Usuario usuario) {
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            if (usuario.getId() != null) {
                usuario.setSenha(findById(usuario.getId()).getSenha());
            } else {
                throw new IllegalArgumentException("Senha não pode ser vazia");
            }
        } else {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        usuarioRepository.save(usuario);
    }

}
