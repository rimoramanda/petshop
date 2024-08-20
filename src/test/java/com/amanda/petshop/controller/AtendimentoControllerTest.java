package com.amanda.petshop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.amanda.petshop.entity.Atendimento;
import com.amanda.petshop.entity.Pet;
import com.amanda.petshop.repository.PetRepository;
import com.amanda.petshop.service.AtendimentoService;
import com.amanda.petshop.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = AtendimentoController.class)
public class AtendimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private AtendimentoService atendimentoService;

    @MockBean
    private PetService petService; // Adicione isso se o PetService for um serviço separado

    @InjectMocks
    private AtendimentoController atendimentoController;

    private Pet pet;
    private Atendimento atendimento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicialize o pet e o atendimento
        pet = new Pet();
        pet.setId(1L);

        atendimento = new Atendimento();
        atendimento.setId(1L);
        atendimento.setPet(pet);
        atendimento.setDescricaoDoAtendimento("Banho");
        atendimento.setValor(BigDecimal.valueOf(50.0));
        atendimento.setData(LocalDate.now());
    }

    @Test
    void testCreateAtendimento() throws Exception {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(atendimentoService.save(any(Atendimento.class))).thenReturn(atendimento);

        mockMvc.perform(post("/atendimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pet\": {\"id\": 1}, \"descricaoDoAtendimento\": \"Banho\", \"valor\": 50.0, \"data\": \"2024-08-20\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricaoDoAtendimento").value("Banho"))
                .andExpect(jsonPath("$.valor").value(50.0));

        verify(atendimentoService).save(any(Atendimento.class));
    }

    @Test
    void testGetAtendimentoById() throws Exception {
        when(atendimentoService.findById(anyLong())).thenReturn(Optional.of(atendimento));

        mockMvc.perform(get("/atendimentos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricaoDoAtendimento").value("Banho"));

        verify(atendimentoService, times(1)).findById(anyLong());
    }

    @Test
    void testGetAllAtendimentos() throws Exception {
        //  atendimentos
        Atendimento atendimento1 = new Atendimento();
        atendimento1.setId(1L);
        atendimento1.setPet(pet);
        atendimento1.setDescricaoDoAtendimento("Banho e Tosa");
        atendimento1.setValor(BigDecimal.valueOf(50.0));
        atendimento1.setData(LocalDate.now());

        Atendimento atendimento2 = new Atendimento();
        atendimento2.setId(2L);
        atendimento2.setPet(pet);
        atendimento2.setDescricaoDoAtendimento("Consulta Veterinária");
        atendimento2.setValor(BigDecimal.valueOf(150.0));
        atendimento2.setData(LocalDate.now());


        List<Atendimento> listaAtendimentos = List.of(atendimento1, atendimento2);


        when(atendimentoService.findAll()).thenReturn(listaAtendimentos);

        // Execução do mockMvc e verificação da resposta
        mockMvc.perform(get("/atendimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(listaAtendimentos.size()))  // Verifica o tamanho da lista retornada
                .andExpect(jsonPath("$[0].descricaoDoAtendimento").value("Banho e Tosa"))
                .andExpect(jsonPath("$[0].valor").value(50.0))
                .andExpect(jsonPath("$[1].descricaoDoAtendimento").value("Consulta Veterinária"))
                .andExpect(jsonPath("$[1].valor").value(150.0));


        verify(atendimentoService, times(1)).findAll();
    }

    @Test
    void testAtualizarAtendimento() throws Exception {
        when(atendimentoService.findById(anyLong())).thenReturn(Optional.of(atendimento));
        when(atendimentoService.save(any(Atendimento.class))).thenReturn(atendimento);
        when(petService.buscarPorId(anyLong())).thenReturn(Optional.of(pet));

        mockMvc.perform(put("/atendimentos/atualiza/atendimento/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricaoDoAtendimento\":\"Consulta Veterinária\", \"valor\":150.00, \"data\":\"2024-08-20\", \"pet\":{\"id\":1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricaoDoAtendimento").value("Consulta Veterinária"))
                .andExpect(jsonPath("$.valor").value(150.00));

        verify(atendimentoService, times(1)).save(any(Atendimento.class));
    }

    @Test
    void testDeleteAtendimento() throws Exception {
        doNothing().when(atendimentoService).deleteById(anyLong());

        mockMvc.perform(delete("/atendimentos/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(atendimentoService, times(1)).deleteById(anyLong());
    }
}
