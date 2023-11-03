package br.ufu.poo2.biblioteca.factory;

public interface Usuario {
    String getTipo();

    void editar(String nome, String email, String matricula, int senha);

    void excluir(String matricula);
}
