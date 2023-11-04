package br.ufu.poo2.biblioteca.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.model.UsuarioAdministrador;
import br.ufu.poo2.biblioteca.model.UsuarioEstudante;
import br.ufu.poo2.biblioteca.model.UsuarioProfessor;

class UsuarioTest {

    @Test
    public void testCriarEditarExcluirProfessor() {
        FabricaDeUsuarios fabricaProfessor = new FabricanteProfessor();
        Usuario professor = fabricaProfessor.criarUsuario("John Doe", "john.doe@example.com", "54ff4");

        assertEquals("John Doe", professor.getNome());
        assertEquals("john.doe@example.com", professor.getEmail());
        assertTrue(professor instanceof UsuarioProfessor);

    }

    @Test
    public void testCriarEditarExcluirAluno() {
        FabricaDeUsuarios fabricaAluno = new FabricanteEstudante();
        Usuario aluno = fabricaAluno.criarUsuario("Jane Doe", "jane.doe@example.com", "119bsi290");

        assertEquals("Jane Doe", aluno.getNome());
        assertEquals("jane.doe@example.com", aluno.getEmail());
        assertTrue(aluno instanceof UsuarioEstudante);
    }

    @Test
    public void testCriarEditarExcluirAdministrador() {
        FabricaDeUsuarios fabricaAdministrador = new FabricanteAdministrador();
        Usuario administrador = fabricaAdministrador.criarUsuario("John Wick", "jown.wick@continental.com", "Daisy");

        assertEquals("John Wick", administrador.getNome());
        assertEquals("jown.wick@continental.com", administrador.getEmail());
        assertTrue(administrador instanceof UsuarioAdministrador);
    }

}