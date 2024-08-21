package com.amanda.petshop.controller;

import com.amanda.petshop.dto.ClienteDTO;
import com.amanda.petshop.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClienteControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void testListarClientes() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Amanda");

        ClienteDTO clienteDTO2 = new ClienteDTO();
        clienteDTO2.setNome("Leticia");

        ClienteDTO clienteDTO3 = new ClienteDTO();
        clienteDTO3.setNome("Heloisa");

        List<ClienteDTO> clientes = List.of(clienteDTO, clienteDTO2, clienteDTO3);

        when(clienteService.listarTodos()).thenReturn(clientes);

        mockMvc.perform(get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Amanda"))
                .andExpect(jsonPath("$[1].nome").value("Leticia"))
                .andExpect(jsonPath("$[2].nome").value("Heloisa"));

        verify(clienteService, times(1)).listarTodos();
    }

    @Test
    void testBuscarClientePorId() throws Exception {
        Long clienteId = 1L;
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Amanda");

        when(clienteService.buscarPorId(clienteId)).thenReturn(Optional.of(clienteDTO));

        mockMvc.perform(get("/clientes/{id}", clienteId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Amanda"));

        verify(clienteService, times(1)).buscarPorId(clienteId);
    }

    @Test
    void testBuscarClientePorIdNaoEncontrado() throws Exception {
        Long clienteId = 123L;

        when(clienteService.buscarPorId(clienteId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/{id}", clienteId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).buscarPorId(clienteId);
    }

    @Test
    void testSalvarCliente() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Amanda");
        clienteDTO.setUsuarioCpf("85562025000");

        when(clienteService.salvar(any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Amanda\",\"usuarioCpf\":\"85562025000\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Amanda"));

        verify(clienteService, times(1)).salvar(any(ClienteDTO.class));
    }

    @Test
    void testAtualizarCliente() throws Exception {
        Long clienteId = 1L;
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Amanda Atualizada");

        when(clienteService.atualizar(eq(clienteId), any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(put("/clientes/{id}", clienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Amanda Atualizada\", \"usuarioCpf\":\"12345678900\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Amanda Atualizada"));

        verify(clienteService, times(1)).atualizar(eq(clienteId), any(ClienteDTO.class));
    }

    @Test
    void testDeletarCliente() throws Exception {
        Long clienteId = 1L;

        doNothing().when(clienteService).deletar(clienteId);

        mockMvc.perform(delete("/clientes/{id}", clienteId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).deletar(clienteId);
    }
}
