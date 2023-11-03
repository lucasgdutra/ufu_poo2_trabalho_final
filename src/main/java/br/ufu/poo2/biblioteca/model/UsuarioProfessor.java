package br.ufu.poo2.biblioteca.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Professor")
public class UsuarioProfessor extends Usuario {
    // campos e métodos específicos de professor
}