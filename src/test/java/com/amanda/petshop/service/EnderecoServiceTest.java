package com.amanda.petshop.service;

import com.amanda.petshop.entity.Endereco;
import com.amanda.petshop.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarEnderecoComSucesso() {
        Endereco endereco = new Endereco();
        endereco.setCidade("Anta");

        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco enderecoSalvo = enderecoService.salvarOuAtualizar((endereco));

        assertNotNull(enderecoSalvo);
        assertEquals("Anta", enderecoSalvo.getCidade());
        verify(enderecoRepository, times(1)).save(endereco);
    }

    @Test
    void testBuscarEnderecoPorId() {
        Long enderecoId = 1L;
        Endereco endereco = new Endereco();
        endereco.setCidade("POA");

        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(endereco));

        Optional<Endereco> enderecoEncontrado = enderecoService.buscarPorId(enderecoId);

        assertTrue(enderecoEncontrado.isPresent());
        assertEquals("POA", enderecoEncontrado.get().getCidade());
    }

    @Test
    void testDeletarEndereco() {
        Long enderecoId = 1L;
        doNothing().when(enderecoRepository).deleteById(enderecoId);

        enderecoService.deletar(enderecoId);

        verify(enderecoRepository, times(1)).deleteById(enderecoId);
    }
}
