package com.amanda.petshop.service;

import com.amanda.petshop.dto.ClienteDTO;
import com.amanda.petshop.entity.Cliente;
import com.amanda.petshop.entity.Usuario;
import com.amanda.petshop.mapper.ClienteMapper;
import com.amanda.petshop.repository.ClienteRepository;
import com.amanda.petshop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public List<ClienteDTO> listarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO);
    }

    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        if (clienteDTO.getUsuarioCpf() == null) {
            throw new IllegalArgumentException("O CPF do usuário não pode ser nulo");
        }

        Usuario usuario = usuarioRepository.findByCpf(clienteDTO.getUsuarioCpf())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente.setUsuario(usuario); // Configura o usuário na entidade Cliente

        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteSalvo);
    }





    public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setNome(clienteDTO.getNome());
        cliente.setDataDeCadastro(clienteDTO.getDataDeCadastro());
        Cliente updatedCliente = clienteRepository.save(cliente);
        return clienteMapper.toDTO(updatedCliente);
    }

    public void deletar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        clienteRepository.delete(cliente);
    }
}
