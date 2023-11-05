package br.ufu.poo2.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufu.poo2.biblioteca.model.Multa;
import br.ufu.poo2.biblioteca.model.TipoUsuario;
import br.ufu.poo2.biblioteca.model.Usuario;
import br.ufu.poo2.biblioteca.service.MultaService;
import br.ufu.poo2.biblioteca.service.UsuarioService;
import br.ufu.poo2.biblioteca.strategy.PagamentoBoleto;
import br.ufu.poo2.biblioteca.strategy.PagamentoCartao;
import br.ufu.poo2.biblioteca.strategy.PagamentoPix;
import br.ufu.poo2.biblioteca.strategy.TiposPagamento;

@Controller
@RequestMapping("/multas")
public class MultaController {

    @Autowired
    private MultaService multaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarMultas(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername(); // get the username of the logged-in user

        Usuario usuario = usuarioService.findByEmail(username);

        List<Multa> multas;
        if (usuario.getTipoUsuario().equals(TipoUsuario.Administrador)) {
            multas = multaService.listarMultas();
        } else {
            multas = multaService.listaMultasPorUsuario(usuario.getId());
        }

        model.addAttribute("multas", multas);

        model.addAttribute("isEdit", false);
        return "multas";
    }

    @PostMapping("/pagar/{id}")
    public String pagarMulta(
            @PathVariable Long id,
            @RequestParam String pagamentoStrategy,
            RedirectAttributes redirectAttributes) {
        // Find the Multa by id
        Multa multa = multaService.findById(id);
        if (multa == null) {
            redirectAttributes.addFlashAttribute("error", "Multa não encontrada!");
            return "redirect:/multas";
        }

        try {
            TiposPagamento tipoPagamento = TiposPagamento.valueOf(pagamentoStrategy);

            switch (tipoPagamento) {
                case CARTAO:
                    multa.setPagamentoStrategy(new PagamentoCartao());
                    break;
                case BOLETO:
                    multa.setPagamentoStrategy(new PagamentoBoleto());
                    break;
                case PIX:
                    multa.setPagamentoStrategy(new PagamentoPix());
                    break;
                default:
                    // This block will never be reached because all enum values are covered above
                    break;
            }
        } catch (IllegalArgumentException e) {
            // Handle the exception for invalid enum values
            redirectAttributes.addFlashAttribute("error", "Método de pagamento inválido!");
        }

        boolean statusPagamento = multa.realizarPagamento();

        if (!statusPagamento) {
            redirectAttributes.addFlashAttribute("error", "Pagamento não realizado! Tente novamente mais tarde, ou utilize outra forma de pagamento.");
            return "redirect:/multas";
        }

        // Process the payment
        multaService.delete(multa);

        // Add a success message
        redirectAttributes.addFlashAttribute("message", "Multa paga com sucesso!");
        return "redirect:/multas";

    }

    @ModelAttribute("tiposPagamento")
    public TiposPagamento[] getTiposPagamento() {
        return TiposPagamento.values();
    }

}
