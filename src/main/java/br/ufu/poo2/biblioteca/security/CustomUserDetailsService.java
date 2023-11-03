package br.ufu.poo2.biblioteca.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("User sent " + username);
        List<Usuario> usuarios = usuarioRepository.findByEmail(username);
        Usuario usuario = usuarios.stream().findFirst().orElse(null);
        if (usuario == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(usuario);
    }
}