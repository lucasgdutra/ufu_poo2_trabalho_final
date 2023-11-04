package br.ufu.poo2.biblioteca.factory;
import br.ufu.poo2.biblioteca.model.Usuario;

public interface FabricaDeUsuarios {
    Usuario criarUsuario(String nome, String email, String senha) ;
}
