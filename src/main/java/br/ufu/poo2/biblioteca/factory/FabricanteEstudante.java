package br.ufu.poo2.biblioteca.factory;

public class FabricanteEstudante implements FabricaDeUsuarios{
    @Override
    public Usuario criarUsuario(String nome, String email, String matricula, int senha) {
        return new UsuarioEstudante(nome, email, matricula, senha);
    }
}
