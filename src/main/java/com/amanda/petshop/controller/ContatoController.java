package com.amanda.petshop.controller;

import com.amanda.petshop.dto.ClienteDTO;
import com.amanda.petshop.entity.Cliente;
import com.amanda.petshop.entity.Contato;
import com.amanda.petshop.mapper.ClienteMapper;
import com.amanda.petshop.service.ClienteService;
import com.amanda.petshop.service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @GetMapping
    public List<Contato> listarTodos() {
        return contatoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarPorId(@PathVariable Long id) {
        Optional<Contato> contato = contatoService.buscarPorId(id);
        return contato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contato> criarContato(@RequestBody Contato contato) {

        if (contato.getCliente() == null || contato.getCliente().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        ClienteDTO clienteDTO = clienteService.buscarPorId(contato.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        //  clienteDTO de volta para a entidade Cliente
        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        // liga o cliente ao contato aqui
        contato.setCliente(cliente);

        Contato novoContato = contatoService.salvarOuAtualizar(contato);

        return ResponseEntity.ok(novoContato);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizar(@PathVariable Long id, @RequestBody Contato contato) {
        if (contatoService.buscarPorId(id).isPresent()) {
            contato.setId(id);
            return ResponseEntity.ok(contatoService.salvarOuAtualizar(contato));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (contatoService.buscarPorId(id).isPresent()) {
            contatoService.deletar(id);
            return ResponseEntity.noContent().build(); // Corrigido para 204 No Content
        }
        return ResponseEntity.notFound().build();
    }
}
