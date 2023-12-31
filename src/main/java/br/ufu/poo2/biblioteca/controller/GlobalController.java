package br.ufu.poo2.biblioteca.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalController {

  @ModelAttribute("servletPath")
  String getRequestServletPath(HttpServletRequest request) {
    return request.getServletPath();
  }
}