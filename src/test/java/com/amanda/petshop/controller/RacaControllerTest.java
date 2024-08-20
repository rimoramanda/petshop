package com.amanda.petshop.controller;


import com.amanda.petshop.entity.Raca;
import com.amanda.petshop.service.RacaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RacaControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RacaController racaController;

    @Mock
    private RacaService racaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(racaController).build();
    }

    @Test
    void testListarRacas() throws Exception {
        Long racaId01 = 1L;
        Raca raca1 = new Raca();
        raca1.setDescricao("Maltes");
        raca1.setId(racaId01);

        Raca raca2 = new Raca();
        Long racaId02 = 2L;
        raca2.setDescricao("Pincher");
        raca2.setId(racaId02);
        List<Raca> racas = List.of(raca1, raca2);

        when(racaService.listarTodos()).thenReturn(racas);

        mockMvc.perform(get("/racas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Maltes"))
                .andExpect(jsonPath("$[1].descricao").value("Pincher"));

        verify(racaService, times(1)).listarTodos();
    }

    @Test
    void testBuscarRacaPorId() throws Exception {
        Long racaId = 1L;
        Raca raca = new Raca();
        raca.setDescricao("Boxer");
        raca.setId(racaId);

        when(racaService.buscarPorId(racaId)).thenReturn(Optional.of(raca));

        mockMvc.perform(get("/racas/{id}", racaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Boxer"));

        verify(racaService, times(1)).buscarPorId(racaId);
    }

    @Test
    void testSalvarRaca() throws Exception {
        Raca raca = new Raca();
        raca.setDescricao("Siames");

        when(racaService.salvarOuAtualizar(any(Raca.class))).thenReturn(raca);

        mockMvc.perform(post("/racas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Siames\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Siames"));

        verify(racaService, times(1)).salvarOuAtualizar(any(Raca.class));
    }


    @Test
    void testAtualizarRaca() throws Exception {
        Long racaId = 19L;
        Raca raca = new Raca();
        raca.setDescricao("Salsicha");
        raca.setId(racaId);

        when(racaService.buscarPorId(racaId)).thenReturn(Optional.of(raca));
        when(racaService.salvarOuAtualizar(any(Raca.class))).thenReturn(raca);

        mockMvc.perform(put("/racas/{id}", racaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Salsicha\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Salsicha"));


        verify(racaService, times(1)).buscarPorId(racaId);
        verify(racaService, times(1)).salvarOuAtualizar(any(Raca.class));
    }


    @Test
    void testDeletarRaca() throws Exception {
        Long racaId = 54L;

        when(racaService.buscarPorId(racaId)).thenReturn(Optional.of(new Raca()));
        doNothing().when(racaService).deletar(racaId);

        mockMvc.perform(delete("/racas/{id}", racaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(racaService, times(1)).buscarPorId(racaId);
        verify(racaService, times(1)).deletar(racaId);
    }

}
