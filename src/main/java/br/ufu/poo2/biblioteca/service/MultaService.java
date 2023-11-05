package br.ufu.poo2.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufu.poo2.biblioteca.model.Multa;
import br.ufu.poo2.biblioteca.repository.MultaRepository;

@Service
public class MultaService {
    private final MultaRepository multaRepository;

    public MultaService(MultaRepository multaRepository) {
        this.multaRepository = multaRepository;
    }

    public void saveMulta(Multa multa) {
        multaRepository.save(multa);
    }

    public List<Multa> listarMultas() {
        return multaRepository.findAll();
    }

    public List<Multa> listaMultasPorUsuario(Long id) {
        return multaRepository.findByUsuarioId(id);
    }

    public Multa findById(Long id) {
        return multaRepository.findById(id).get();
    }

    public void delete(Multa multa) {
        multaRepository.delete(multa);
    }

}
