package br.ufu.poo2.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Root {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}