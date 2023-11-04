package br.ufu.poo2.biblioteca.factory;

import org.springframework.stereotype.Component;

import br.ufu.poo2.biblioteca.model.UsuarioEstudante;

@Component
public class FabricanteEstudante implements FabricaDeUsuarios {
    @Override
    public UsuarioEstudante criarUsuario(String nome, String email, String senha) {
        UsuarioEstudante usuario = new UsuarioEstudante();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
