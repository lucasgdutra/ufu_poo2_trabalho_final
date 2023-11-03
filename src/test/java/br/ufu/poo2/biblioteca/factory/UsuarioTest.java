package br.ufu.poo2.biblioteca.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    public void testCriarEditarExcluirProfessor() {
        FabricaDeUsuarios fabricaProfessor = new FabricanteProfessor();
        Usuario professor = fabricaProfessor.criarUsuario("John Doe", "john.doe@example.com", "54ff4", 8975);

        assertEquals("Professor", professor.getTipo());

        professor.editar("John Smith", "john.smith@example.com", "119bsi290", 123456);
        assertEquals("John Smith", ((UsuarioProfessor) professor).getNome());
        assertEquals("john.smith@example.com", ((UsuarioProfessor) professor).getEmail());

        professor.excluir("john.smith@example.com");
    }

    @Test
    public void testCriarEditarExcluirAluno() {
        FabricaDeUsuarios fabricaAluno = new FabricanteEstudante();
        Usuario aluno = fabricaAluno.criarUsuario("Jane Doe", "jane.doe@example.com", "119bsi290", 123456);

        assertEquals("Aluno", aluno.getTipo());

        aluno.editar("Jane Smith", "jane.smith@example.com", "119bsi290", 123456);
        assertEquals("Jane Smith", ((UsuarioEstudante) aluno).getNome());
        assertEquals("jane.smith@example.com", ((UsuarioEstudante) aluno).getEmail());

        aluno.excluir("jane.smith@example.com");
    }

}