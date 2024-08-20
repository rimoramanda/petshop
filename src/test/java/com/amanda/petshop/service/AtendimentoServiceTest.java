package com.amanda.petshop.service;

import com.amanda.petshop.entity.Atendimento;
import com.amanda.petshop.repository.AtendimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AtendimentoServiceTest {

    @InjectMocks
    private AtendimentoService atendimentoService;

    @Mock
    private AtendimentoRepository atendimentoRepository;

    private Atendimento atendimento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        atendimento = new Atendimento(1L, null, "Banho e Tosa", new BigDecimal("100.00"), LocalDate.now());
    }

    @Test
    void testSaveAtendimento() {
        when(atendimentoRepository.save(any(Atendimento.class))).thenReturn(atendimento);

        Atendimento savedAtendimento = atendimentoService.save(atendimento);

        assertEquals(atendimento.getId(), savedAtendimento.getId());
        assertEquals(atendimento.getDescricaoDoAtendimento(), savedAtendimento.getDescricaoDoAtendimento());
        verify(atendimentoRepository, times(1)).save(atendimento);
    }

    @Test
    void testFindById() {
        when(atendimentoRepository.findById(1L)).thenReturn(Optional.of(atendimento));

        Optional<Atendimento> foundAtendimento = atendimentoService.findById(1L);

        assertTrue(foundAtendimento.isPresent());
        assertEquals(atendimento.getId(), foundAtendimento.get().getId());
        verify(atendimentoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        atendimentoService.findAll();
        verify(atendimentoRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        doNothing().when(atendimentoRepository).deleteById(1L);

        atendimentoService.deleteById(1L);

        verify(atendimentoRepository, times(1)).deleteById(1L);
    }
}
