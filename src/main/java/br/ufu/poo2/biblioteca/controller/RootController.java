package br.ufu.poo2.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String redirectLivros(Model model) {

        return "redirect:/livros";
    }

}
