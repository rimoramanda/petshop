package com.amanda.petshop.service;

import com.amanda.petshop.entity.Contato;
import com.amanda.petshop.repository.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContatoServiceTest {

    @InjectMocks
    private ContatoService contatoService;

    @Mock
    private ContatoRepository contatoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarContatoComSucesso() {
        Contato contato = new Contato();
        contato.setTipo("123456789");

        when(contatoRepository.save(contato)).thenReturn(contato);

        Contato contatoSalvo = contatoService.salvarOuAtualizar((contato));

        assertNotNull(contatoSalvo);
        assertEquals("123456789", contatoSalvo.getTipo());
        verify(contatoRepository, times(1)).save(contato);
    }

    @Test
    void testBuscarContatoPorId() {
        Long contatoId = 1L;
        Contato contato = new Contato();
        contato.setTipo("123456789");

        when(contatoRepository.findById(contatoId)).thenReturn(Optional.of(contato));

        Optional<Contato> contatoEncontrado = contatoService.buscarPorId(contatoId);

        assertTrue(contatoEncontrado.isPresent());
        assertEquals("123456789", contatoEncontrado.get().getTipo());
    }

    @Test
    void testDeletarContato() {
        Long contatoId = 1L;
        doNothing().when(contatoRepository).deleteById(contatoId);

        contatoService.deletar(contatoId);

        verify(contatoRepository, times(1)).deleteById(contatoId);
    }
}
