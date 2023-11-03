package br.ufu.poo2.biblioteca.factory;

public interface FabricaDeUsuarios {
    Usuario criarUsuario(String nome, String email, String matricula, int senha);
}
