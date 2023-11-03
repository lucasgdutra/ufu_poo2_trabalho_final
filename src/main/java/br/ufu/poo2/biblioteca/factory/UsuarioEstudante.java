package br.ufu.poo2.biblioteca.factory;

public class UsuarioEstudante implements Usuario{
    private String nome;
    private String email;
    private String matricula;
    private int senha;

    public UsuarioEstudante(String nome, String email, String matricula, int senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.matricula = matricula;
    }

    @Override
    public String getTipo() {
        return "Aluno";
    }

    @Override
    public void editar(String nome, String email, String matricula, int senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.matricula = matricula;
    }

    @Override
    public void excluir(String matricula) {
        if (this.matricula.equals(matricula)) {
            System.out.println("Aluno excluído: " + this.matricula);
        } else {
            System.out.println("Matrícula não corresponde ao Aluno.");
        }
    }

    public int getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

