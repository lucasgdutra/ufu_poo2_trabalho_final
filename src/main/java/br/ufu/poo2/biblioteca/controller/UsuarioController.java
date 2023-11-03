package br.ufu.poo2.biblioteca.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufu.poo2.biblioteca.dto.UsuarioForm;
import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.model.UsuarioAdministrador;
import br.ufu.poo2.biblioteca.model.UsuarioEstudante;
import br.ufu.poo2.biblioteca.model.UsuarioProfessor;
import br.ufu.poo2.biblioteca.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuario", new UsuarioEstudante()); // This line adds an empty Usuario object to the model
        model.addAttribute("isEdit", false);

        return "usuarios"; // The name of the Thymeleaf template
    }

    @GetMapping("/edit/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable Long id, Model model) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("usuario", usuario);
        model.addAttribute("isEdit", true);
        return "usuarios"; // The name of the Thymeleaf template for editing a Usuario
    }

    @GetMapping("/delete/{id}")
    public String deletarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.findById(id);
        usuarioService.deleteUsuario(usuario);
        redirectAttributes.addFlashAttribute("message", "Usuário deletado com sucesso!");
        return "redirect:/usuarios";
    }

    @PostMapping
    public String adicionarOuAtualizarUsuario(@RequestParam String tipoUsuario,
            @Valid @ModelAttribute("usuario") UsuarioForm form, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Handle errors here. For simplicity, we'll just return to the form page.
            return "usuarios";
        }
        Usuario usuario;
        switch (tipoUsuario) {
            case "Estudante":
                usuario = new UsuarioEstudante();
                break;
            case "Professor":
                usuario = new UsuarioProfessor();
                break;
            case "Administrador":
                usuario = new UsuarioAdministrador();
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuário inválido");
        }
        usuario.setId(form.getId());
        usuario.setNome(form.getNome());
        usuario.setEmail(form.getEmail());

        usuarioService.saveUsuario(usuario);

        String successMessage = usuario.getId() == null ? "Usuario adicionado com sucesso!"
                : "Usuario atualizado com sucesso!";
        redirectAttributes.addFlashAttribute("message", successMessage);
        return "redirect:/usuarios";
    }
}
