package br.ufu.poo2.biblioteca.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.model.UsuarioAdministrador;
import br.ufu.poo2.biblioteca.repository.UsuarioRepository;

@Component
public class Bootstrap {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if (usuarioRepository.findByEmail("admin@ufu.br").isEmpty()) {
            Usuario admin = new UsuarioAdministrador();
            admin.setEmail("admin@ufu.br");
            admin.setSenha(passwordEncoder.encode("admin"));
            admin.setNome("Administrador");
            usuarioRepository.save(admin);
        }

    }
}
