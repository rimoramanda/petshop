package com.amanda.petshop.service;

import com.amanda.petshop.entity.Raca;
import com.amanda.petshop.repository.RacaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RacaServiceTest {

    @InjectMocks
    private RacaService racaService;

    @Mock
    private RacaRepository racaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarRacaComSucesso() {
        Raca raca = new Raca();
        raca.setDescricao("Raca1");

        when(racaRepository.save(raca)).thenReturn(raca);

        Raca racaSalva = racaService.salvarOuAtualizar(raca);

        assertNotNull(racaSalva);
        assertEquals("Raca1", racaSalva.getDescricao());
        verify(racaRepository, times(1)).save(raca);
    }

    @Test
    void testBuscarRacaPorId() {
        Long racaId = 1L;
        Raca raca = new Raca();
        raca.setDescricao("Raca1");

        when(racaRepository.findById(racaId)).thenReturn(Optional.of(raca));

        Optional<Raca> racaEncontrada = racaService.buscarPorId(racaId);

        assertTrue(racaEncontrada.isPresent());
        assertEquals("Raca1", racaEncontrada.get().getDescricao());
    }

    @Test
    void testDeletarRaca() {
        Long racaId = 1L;
        doNothing().when(racaRepository).deleteById(racaId);

        racaService.deletar(racaId);

        verify(racaRepository, times(1)).deleteById(racaId);
    }
}
