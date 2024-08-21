package com.amanda.petshop.controller;

import com.amanda.petshop.entity.Pet;
import com.amanda.petshop.service.PetService;
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

public class PetControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PetController petController;

    @Mock
    private PetService petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void testListarPets() throws Exception {
        Pet pet1 = new Pet();
        pet1.setNome("Pet 1");

        Pet pet2 = new Pet();
        pet2.setNome("Pet 2");

        List<Pet> pets = List.of(pet1, pet2);

        when(petService.listarTodos()).thenReturn(pets);

        mockMvc.perform(get("/pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Pet 1"))
                .andExpect(jsonPath("$[1].nome").value("Pet 2"));

        verify(petService, times(1)).listarTodos();
    }

    @Test
    void testBuscarPetPorId() throws Exception {
        Long petId = 88L;
        Pet pet = new Pet();
        pet.setNome("Rebeca");
        pet.setId(petId);

        when(petService.buscarPorId(petId)).thenReturn(Optional.of(pet));

        mockMvc.perform(get("/pets/{id}", petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Rebeca"));

        verify(petService, times(1)).buscarPorId(petId);
    }

    @Test
    void testSalvarPet() throws Exception {
        Long Id = 25L;
        Pet pet = new Pet();
        pet.setNome("Vick");
        pet.setId(Id);

        when(petService.salvarOuAtualizar(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Vick\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Vick"));

        verify(petService, times(1)).salvarOuAtualizar(any(Pet.class));
    }

    @Test
    void testAtualizarPet() throws Exception {
        Long petId = 999L;

        Pet pet = new Pet();
        pet.setId(petId);
        pet.setNome("Micha NovaAtual");

        when(petService.salvarOuAtualizar(any(Pet.class))).thenReturn(pet);
        when(petService.buscarPorId(petId)).thenReturn(Optional.of(pet));

        mockMvc.perform(put("/pets/{id}", petId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Micha NovaAtual\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Micha NovaAtual"))
                .andExpect(jsonPath("$.id").value(petId));

        verify(petService, times(1)).salvarOuAtualizar(any(Pet.class));
    }



    @Test
    void testDeletarPet() throws Exception {
        Long petId = 333L;

        doNothing().when(petService).deletar(petId);
        when(petService.buscarPorId(petId)).thenReturn(Optional.of(new Pet()));

        mockMvc.perform(delete("/pets/{id}", petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(petService, times(1)).deletar(petId);
        verify(petService, times(1)).buscarPorId(petId);
    }

    @Test
    void testDeletarPetNaoEncontrado() throws Exception {
        Long petId = 77L;


        when(petService.buscarPorId(petId)).thenReturn(Optional.empty());


        mockMvc.perform(delete("/pets/{id}", petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(petService, never()).deletar(petId);
        verify(petService).buscarPorId(petId);
    }


}
