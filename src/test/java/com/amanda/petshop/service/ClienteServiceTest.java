package com.amanda.petshop.service;

import com.amanda.petshop.dto.ClienteDTO;
import com.amanda.petshop.entity.Cliente;
import com.amanda.petshop.entity.Usuario;
import com.amanda.petshop.mapper.ClienteMapper;
import com.amanda.petshop.repository.ClienteRepository;
import com.amanda.petshop.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarClienteComSucesso() {
        Usuario usuario = new Usuario();
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Amanda");
        clienteDTO.setUsuarioCpf(usuario.getCpf());

        Cliente cliente = new Cliente();
        cliente.setNome("Amanda");
        cliente.setUsuario(usuario);

        when(usuarioRepository.findByCpf(usuario.getCpf())).thenReturn((usuario));
        when(clienteMapper.toEntity(clienteDTO)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO clienteSalvoDTO = clienteService.salvar(clienteDTO);

        assertNotNull(clienteSalvoDTO);
        assertEquals("Amanda", clienteSalvoDTO.getNome());
        verify(clienteRepository, times(1)).save(cliente);
        verify(clienteMapper, times(1)).toEntity(clienteDTO);
        verify(clienteMapper, times(1)).toDTO(cliente);
    }

    @Test
    void testSalvarClienteUsuarioNaoEncontrado() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setUsuarioCpf("12345678900");

        when(usuarioRepository.findByCpf(clienteDTO.getUsuarioCpf())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clienteService.salvar(clienteDTO));
        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}
