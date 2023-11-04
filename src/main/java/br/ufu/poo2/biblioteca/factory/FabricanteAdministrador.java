package br.ufu.poo2.biblioteca.factory;

import org.springframework.stereotype.Component;

import br.ufu.poo2.biblioteca.model.UsuarioAdministrador;

@Component
public class FabricanteAdministrador implements FabricaDeUsuarios {
    @Override
    public UsuarioAdministrador criarUsuario(String nome, String email, String senha) {
        UsuarioAdministrador usuario = new UsuarioAdministrador();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
