package com.amanda.petshop.controller;

import com.amanda.petshop.entity.Pet;
import com.amanda.petshop.service.PetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pets")
public class PetController {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }
    @GetMapping
    public List<Pet> listarTodos() {
        return petService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
        Optional<Pet> pet = petService.buscarPorId(id);
        return pet.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Pet> salvar(@RequestBody Pet pet) {
        if (pet.getId() != null) {
            return ResponseEntity.badRequest().build(); // ou outra lógica para id não nulo
        }
        Pet savedPet = petService.salvarOuAtualizar(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> atualizar(@PathVariable Long id, @RequestBody Pet pet) {
        Optional<Pet> petExistente = petService.buscarPorId(id);
        if (petExistente.isPresent()) {
            pet.setId(id);
            Pet updatedPet = petService.salvarOuAtualizar(pet);
            return ResponseEntity.ok(updatedPet);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (petService.buscarPorId(id).isPresent()) {
            petService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

