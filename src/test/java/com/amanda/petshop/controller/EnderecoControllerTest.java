package com.amanda.petshop.controller;

import com.amanda.petshop.entity.Endereco;
import com.amanda.petshop.service.EnderecoService;
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

public class EnderecoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EnderecoController enderecoController;

    @Mock
    private EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(enderecoController).build();
    }

    @Test
    void testListarEnderecos() throws Exception {
        Endereco endereco1 = new Endereco();
        endereco1.setCidade("Farroupilha");

        Endereco endereco2 = new Endereco();
        endereco2.setCidade("Caxias");

        List<Endereco> enderecos = List.of(endereco1, endereco2);

        when(enderecoService.listarTodos()).thenReturn(enderecos);

        mockMvc.perform(get("/enderecos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cidade").value("Farroupilha"))
                .andExpect(jsonPath("$[1].cidade").value("Caxias"));

        verify(enderecoService, times(1)).listarTodos();
    }

    @Test
    void testBuscarEnderecoPorId() throws Exception {
        Long enderecoId = 1024L;
        Endereco endereco = new Endereco();
        endereco.setCidade("Guaiba");

        when(enderecoService.buscarPorId(enderecoId)).thenReturn(Optional.of(endereco));

        mockMvc.perform(get("/enderecos/{id}", enderecoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade").value("Guaiba"));

        verify(enderecoService, times(1)).buscarPorId(enderecoId);
    }

    @Test
    void testSalvarEndereco() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setCidade("POA");

        when(enderecoService.salvarOuAtualizar(any(Endereco.class))).thenReturn(endereco);

        mockMvc.perform(post("/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cidade\":\"POA\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cidade").value("POA"));

        verify(enderecoService, times(1)).salvarOuAtualizar(any(Endereco.class));
    }

    @Test
    void testAtualizarEndereco() throws Exception {
        Long enderecoId = 1L;

        Endereco endereco = new Endereco();
        endereco.setId(enderecoId);
        endereco.setLogradouro("Rua Nova Beira");

        when(enderecoService.buscarPorId(enderecoId)).thenReturn(Optional.of(endereco));
        when(enderecoService.salvarOuAtualizar(any(Endereco.class))).thenReturn(endereco);

        mockMvc.perform(put("/enderecos/{id}", enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"logradouro\":\"Rua Nova Beira\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.logradouro").value("Rua Nova Beira"))
                .andExpect(jsonPath("$.id").value(enderecoId));

        verify(enderecoService, times(1)).buscarPorId(enderecoId);
        verify(enderecoService, times(1)).salvarOuAtualizar(any(Endereco.class));
    }


    @Test
    void testDeletarEndereco() throws Exception {
        Long enderecoId = 696L;


        when(enderecoService.buscarPorId(enderecoId)).thenReturn(Optional.of(new Endereco()));
        doNothing().when(enderecoService).deletar(enderecoId);

        mockMvc.perform(delete("/enderecos/{id}", enderecoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(enderecoService, times(1)).buscarPorId(enderecoId);
        verify(enderecoService, times(1)).deletar(enderecoId);
    }

}
