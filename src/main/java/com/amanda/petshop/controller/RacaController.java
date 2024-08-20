package com.amanda.petshop.controller;

import com.amanda.petshop.entity.Raca;
import com.amanda.petshop.service.RacaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/racas")
public class RacaController {

    @Autowired
    private RacaService racaService;

    @GetMapping
    public List<Raca> listarTodos() {
        return racaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Raca> buscarPorId(@PathVariable Long id) {
        Optional<Raca> raca = racaService.buscarPorId(id);
        return raca.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Raca> salvar(@RequestBody Raca raca) {
        Raca racaSalva = racaService.salvarOuAtualizar(raca);
        return ResponseEntity.status(HttpStatus.CREATED).body(racaSalva);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Raca> atualizar(@PathVariable Long id, @RequestBody Raca raca) {
        if (racaService.buscarPorId(id).isPresent()) {
            raca.setId(id);
            return ResponseEntity.ok(racaService.salvarOuAtualizar(raca));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (racaService.buscarPorId(id).isPresent()) {
            racaService.deletar(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}