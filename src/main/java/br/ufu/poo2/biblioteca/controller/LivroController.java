package br.ufu.poo2.biblioteca.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import br.ufu.poo2.biblioteca.model.Livro;
import br.ufu.poo2.biblioteca.service.LivroService;

@Controller
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;

    @GetMapping
    public String listarLivros(Model model) {
        List<Livro> livros = livroService.listarLivros();
        model.addAttribute("livros", livros);
        model.addAttribute("livro", new Livro()); // This line adds an empty Livro object to the model
        model.addAttribute("isEdit", false);

        return "livros"; // The name of the Thymeleaf template
    }

    @GetMapping("/edit/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable Long id, Model model) {
        List<Livro> livros = livroService.listarLivros();
        Livro livro = livroService.findById(id);
        model.addAttribute("livros", livros);
        model.addAttribute("livro", livro);
        model.addAttribute("isEdit", true);
        return "livros"; // The name of the Thymeleaf template for editing a Livro
    }

    @GetMapping("/delete/{id}")
    public String deletarLivro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
        Livro livro = livroService.findById(id);
        livroService.deleteLivro(livro);    
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao deletar livro, verifique se existem emprestimos ou multas pendentes");
            return "redirect:/livros";
        }
        
        redirectAttributes.addFlashAttribute("message", "Livro deletado com sucesso!");
        return "redirect:/livros";
    }

    @PostMapping
    public String adicionarOuAtualizarLivro(@Valid @ModelAttribute("livro") Livro livro, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Handle errors here. For simplicity, we'll just return to the form page.
            return "livros";
        }

        livroService.saveLivro(livro);

        String successMessage = livro.getId() == null ? "Livro adicionado com sucesso!"
                : "Livro atualizado com sucesso!";
        redirectAttributes.addFlashAttribute("message", successMessage);
        return "redirect:/livros";
    }
}
