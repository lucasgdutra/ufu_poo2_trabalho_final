package br.ufu.poo2.biblioteca.factory;

import org.springframework.stereotype.Component;

import br.ufu.poo2.biblioteca.model.UsuarioProfessor;

@Component
public class FabricanteProfessor implements FabricaDeUsuarios {
    @Override
    public UsuarioProfessor criarUsuario(String nome, String email, String senha) {
        UsuarioProfessor usuario = new UsuarioProfessor();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        return usuario;
    }
}
