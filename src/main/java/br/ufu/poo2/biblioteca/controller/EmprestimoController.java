package br.ufu.poo2.biblioteca.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufu.poo2.biblioteca.dto.EmprestimoForm;
import br.ufu.poo2.biblioteca.factory.FabricanteEstudante;
import br.ufu.poo2.biblioteca.model.Emprestimo;
import br.ufu.poo2.biblioteca.model.EmprestimoEstudante;
import br.ufu.poo2.biblioteca.model.EmprestimoProfessor;
import br.ufu.poo2.biblioteca.model.Livro;
import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.service.EmprestimoService;
import br.ufu.poo2.biblioteca.service.LivroService;
import br.ufu.poo2.biblioteca.service.UsuarioService;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {
    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroService livroService;

    @Autowired
    private FabricanteEstudante fabricanteEstudante;

    @GetMapping
    public String listarEmprestimos(Model model) {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        model.addAttribute("emprestimos", emprestimos);
        Emprestimo emprestimo = new EmprestimoEstudante();
        emprestimo.setUsuario(fabricanteEstudante.criarUsuario(null, null, null));
        Livro livro = new Livro();
        emprestimo.setLivro(livro);
        model.addAttribute("emprestimo", emprestimo); // This line adds an empty Emprestimo object to the
                                                      // model
        model.addAttribute("isEdit", false);

        return "emprestimos";
    }

    @GetMapping("/edit/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable Long id, Model model) {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        Emprestimo emprestimo = emprestimoService.findById(id);
        model.addAttribute("emprestimos", emprestimos);
        model.addAttribute("emprestimo", emprestimo);
        model.addAttribute("isEdit", true);
        return "emprestimos"; // The name of the Thymeleaf template for editing a Livro
    }

    @GetMapping("/delete/{id}")
    public String deletarEmprestimo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Emprestimo emprestimo = emprestimoService.findById(id);
        emprestimoService.deleteEmprestimo(emprestimo);
        redirectAttributes.addFlashAttribute("message", "Emprestimo deletado com sucesso!");
        return "redirect:/emprestimos";
    }

    @PostMapping
    public String adicionarOuAtualizarEmprestimo(
            @Valid @ModelAttribute("emprestimo") EmprestimoForm form, Authentication authentication,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        System.out.println("Tipo de emprestimo: " + form.getTipoEmprestimo());
        System.out.println("Usuario: " + form.getUsuarioId());
        System.out.println("Livro: " + form.getLivroId());
        System.out.println("Data de emprestimo: " + form.getDataEmprestimo());
        System.out.println("Data de devolucao: " + form.getDataDevolucao());

        String tipoEmprestimo = form.getTipoEmprestimo();

        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                if (role.equals("ROLE_Estudante")) {
                    tipoEmprestimo = "Estudante";
                } else if (role.equals("ROLE_Professor")) {
                    tipoEmprestimo = "Professor";
                }
                // If the user is an administrator, tipoEmprestimo remains as received from the
                // form
            }
        }

        if (result.hasErrors()) {
            // Handle errors here. For simplicity, we'll just return to the form page.
            return "emprestimos";
        }
        Emprestimo emprestimo;

        switch (tipoEmprestimo) {
            case "Estudante":
                emprestimo = new EmprestimoEstudante();
                break;
            case "Professor":
                emprestimo = new EmprestimoProfessor();
                break;
            default:
                throw new IllegalArgumentException("Tipo de emprestimo inválido");
        }
        emprestimo.setId(form.getId());
        Usuario usuario = usuarioService.findById(form.getUsuarioId());
        emprestimo.setUsuario(usuario);

        Livro livro = livroService.findById(form.getLivroId());
        emprestimo.setLivro(livro);

        emprestimo.setDataEmprestimo(form.getDataEmprestimo());
        emprestimo.setDataDevolucao(form.getDataDevolucao());

        emprestimoService.saveEmprestimo(emprestimo);

        String successMessage = emprestimo.getId() == null ? "Emprestimo adicionado com sucesso!"
                : "Emprestimo atualizado com sucesso!";
        redirectAttributes.addFlashAttribute("message", successMessage);
        return "redirect:/emprestimos";
    }

    // @ModelAttribute
    // public void resolveUsuario(@RequestParam(value = "usuarioType", required =
    // false) String usuarioType, Model model) {
    // Usuario usuario = null;
    // if ("Professor".equalsIgnoreCase(usuarioType)) {
    // usuario = fabricanteProfessor.criarUsuario(null, null, null);
    // } else if ("Estudante".equalsIgnoreCase(usuarioType)) {
    // usuario = fabricanteEstudante.criarUsuario(null, null, null);
    // } else if ("Administrador".equalsIgnoreCase(usuarioType)) {
    // usuario = fabricanteAdministrador.criarUsuario(null, null, null);
    // }
    // model.addAttribute("usuario", usuario); // This line adds a resolved Usuario
    // object to the model
    // }
}
