package com.amanda.petshop.controller;

import com.amanda.petshop.entity.Atendimento;
import com.amanda.petshop.entity.Pet;
import com.amanda.petshop.repository.PetRepository;
import com.amanda.petshop.service.AtendimentoService;
import com.amanda.petshop.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {


    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<Atendimento> createAtendimento(@RequestBody Atendimento atendimento) {
        if (atendimento.getPet() == null || atendimento.getPet().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Pet pet = petRepository.findById(atendimento.getPet().getId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        atendimento.setPet(pet);
        Atendimento createdAtendimento = atendimentoService.save(atendimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAtendimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atendimento> getAtendimentoById(@PathVariable Long id) {
        Optional<Atendimento> atendimento = atendimentoService.findById(id);
        return atendimento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Atendimento>> getAllAtendimentos() {
        List<Atendimento> atendimentos = atendimentoService.findAll();
        return ResponseEntity.ok(atendimentos);
    }

    @PutMapping("/atualiza/atendimento/{id}")
    public ResponseEntity<Atendimento> atualizarAtendimento(
            @PathVariable Long id,
            @RequestBody Atendimento atendimentoAtualizado) {

        Optional<Atendimento> atendimentoOptional = atendimentoService.findById(id);
        if (atendimentoOptional.isPresent()) {
            Atendimento atendimentoExistente = atendimentoOptional.get();

            atendimentoExistente.setDescricaoDoAtendimento(atendimentoAtualizado.getDescricaoDoAtendimento());
            atendimentoExistente.setValor(atendimentoAtualizado.getValor());
            atendimentoExistente.setData(atendimentoAtualizado.getData());

            if (atendimentoAtualizado.getPet() != null && atendimentoAtualizado.getPet().getId() != null) {
                Pet pet = petService.buscarPorId(atendimentoAtualizado.getPet().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Pet not found"));
                atendimentoExistente.setPet(pet);
            }

            Atendimento atendimentoSalvo = atendimentoService.save(atendimentoExistente);
            return ResponseEntity.ok(atendimentoSalvo);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtendimento(@PathVariable Long id) {
        atendimentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


