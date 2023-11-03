package br.ufu.poo2.biblioteca.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Administrador")
public class UsuarioAdministrador extends Usuario {

}
