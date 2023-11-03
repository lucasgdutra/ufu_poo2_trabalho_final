package br.ufu.poo2.biblioteca.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Estudante")
public class UsuarioEstudante extends Usuario {
    // campos e métodos específicos de estudante
}
