package com.amanda.petshop.service;

import com.amanda.petshop.entity.Raca;
import com.amanda.petshop.repository.RacaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RacaService {

    @Autowired
    private RacaRepository racaRepository;

    public List<Raca> listarTodos() {
        return racaRepository.findAll();
    }

    public Optional<Raca> buscarPorId(Long id) {
        return racaRepository.findById(id);
    }

    public Raca salvarOuAtualizar(Raca raca) {
        return racaRepository.save(raca);
    }

    public void deletar(Long id) {
        racaRepository.deleteById(id);
    }
}

