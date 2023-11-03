package br.ufu.poo2.biblioteca.factory;

public class FabricanteProfessor implements FabricaDeUsuarios{
    @Override
    public Usuario criarUsuario(String nome, String email, String matricula, int senha) {
        return new UsuarioProfessor(nome, email, matricula, senha);
    }
}
