package com.amanda.petshop.controller;

import com.amanda.petshop.dto.ClienteDTO;
import com.amanda.petshop.entity.Cliente;
import com.amanda.petshop.entity.Contato;
import com.amanda.petshop.mapper.ClienteMapper;
import com.amanda.petshop.service.ClienteService;
import com.amanda.petshop.service.ContatoService;
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

public class ContatoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ContatoController contatoController;

    @Mock
    private ContatoService contatoService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteMapper clienteMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contatoController).build();
    }

    @Test
    void testListarContatos() throws Exception {
        Contato contato1 = new Contato();
        contato1.setTipo("amanda@gmail.com");

        Contato contato2 = new Contato();
        contato2.setTipo("987654321");

        List<Contato> contatos = List.of(contato1, contato2);

        when(contatoService.listarTodos()).thenReturn(contatos);

        mockMvc.perform(get("/contatos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("amanda@gmail.com"))
                .andExpect(jsonPath("$[1].tipo").value("987654321"));

        verify(contatoService, times(1)).listarTodos();
    }

    @Test
    void testBuscarContatoPorId() throws Exception {
        Long contatoId = 666L;
        Contato contato = new Contato();
        contato.setTipo("amanda@gmail.com");

        when(contatoService.buscarPorId(contatoId)).thenReturn(Optional.of(contato));

        mockMvc.perform(get("/contatos/{id}", contatoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("amanda@gmail.com"));

        verify(contatoService, times(1)).buscarPorId(contatoId);
    }

    @Test
    void testSalvarContato() throws Exception {
        Long clienteId = 666L; // Defina um ID de cliente fictício
        Contato contato = new Contato();
        contato.setTipo("amanda@gmail.com");
        contato.setCliente(new Cliente()); // Defina o cliente associado ao contato

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(clienteId);

        when(clienteService.buscarPorId(clienteId)).thenReturn(Optional.of(clienteDTO));
        when(contatoService.salvarOuAtualizar(any(Contato.class))).thenReturn(contato);

        mockMvc.perform(post("/contatos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tipo\":\"amanda@gmail.com\", \"cliente\":{\"id\":666}}"))
                .andExpect(status().isOk()) // O status esperado é 200 OK
                .andExpect(jsonPath("$.tipo").value("amanda@gmail.com"));

        verify(clienteService, times(1)).buscarPorId(clienteId);
        verify(contatoService, times(1)).salvarOuAtualizar(any(Contato.class));
    }


    @Test
    void testAtualizarContato() throws Exception {
        Long contatoId = 999L;
        Contato contato = new Contato();
        contato.setId(contatoId);
        contato.setTipo("987654321");

        when(contatoService.salvarOuAtualizar(any(Contato.class))).thenReturn(contato);
        when(contatoService.buscarPorId(contatoId)).thenReturn(Optional.of(contato));

        mockMvc.perform(put("/contatos/{id}", contatoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tipo\":\"987654321\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("987654321"));

        verify(contatoService, times(1)).salvarOuAtualizar(any(Contato.class));
    }

    @Test
    void testDeletarContato() throws Exception {
        Long contatoId = 666L;

        when(contatoService.buscarPorId(contatoId)).thenReturn(Optional.of(new Contato()));

        mockMvc.perform(delete("/contatos/{id}", contatoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(contatoService, times(1)).deletar(contatoId);
    }
}
