package com.amanda.petshop.service;

import com.amanda.petshop.entity.Contato;
import com.amanda.petshop.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    public List<Contato> listarTodos() {
        return contatoRepository.findAll();
    }

    public Optional<Contato> buscarPorId(Long id) {
        return contatoRepository.findById(id);
    }

    public Contato salvarOuAtualizar(Contato contato) {
        return contatoRepository.save(contato);
    }

    public void deletar(Long id) {
        contatoRepository.deleteById(id);
    }
}
