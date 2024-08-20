package com.amanda.petshop.service;



import com.amanda.petshop.entity.Atendimento;
import com.amanda.petshop.repository.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    public Atendimento save(Atendimento atendimento) {
        return atendimentoRepository.save(atendimento);
    }

    public Optional<Atendimento> findById(Long id) {
        return atendimentoRepository.findById(id);
    }

    public List<Atendimento> findAll() {
        return atendimentoRepository.findAll();
    }

    public void deleteById(Long id) {
        atendimentoRepository.deleteById(id);
    }
}
