package com.amanda.petshop.controller;

import com.amanda.petshop.entity.Endereco;
import com.amanda.petshop.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public List<Endereco> listarTodos() {
        return enderecoService.listarTodos();
    }

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoService.buscarPorId(id);
        return endereco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Endereco> salvar(@RequestBody Endereco endereco) {
        Endereco savedEndereco = enderecoService.salvarOuAtualizar(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEndereco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id, @RequestBody Endereco endereco) {
        return enderecoService.buscarPorId(id)
                .map(existingEndereco -> {
                    endereco.setId(id);
                    Endereco updatedEndereco = enderecoService.salvarOuAtualizar(endereco);
                    return ResponseEntity.ok(updatedEndereco);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (enderecoService.buscarPorId(id).isPresent()) {
            enderecoService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

