package com.amanda.petshop.service;

import com.amanda.petshop.entity.Cliente;
import com.amanda.petshop.entity.Pet;
import com.amanda.petshop.entity.Raca;
import com.amanda.petshop.repository.PetRepository;
import jakarta.persistence.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarPetComSucesso() {
        Long petId = 333L;

        Pet pet = new Pet();
        pet.setNome("Marley");
        pet.setId(petId);

        when(petRepository.save(pet)).thenReturn(pet);

        Pet petSalvo = petService.salvarOuAtualizar(pet);

        assertNotNull(petSalvo);
        assertEquals("Marley", petSalvo.getNome());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testBuscarPetPorId() {
        Long petId = 33L;
        Pet pet = new Pet();
        pet.setNome("Micha");

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        Optional<Pet> petEncontrado = petService.buscarPorId(petId);

        assertTrue(petEncontrado.isPresent());
        assertEquals("Micha", petEncontrado.get().getNome());
    }

    @Test
    void testDeletarPet() {
        Long petId = 666L;
        doNothing().when(petRepository).deleteById(petId);

        petService.deletar(petId);

        verify(petRepository, times(1)).deleteById(petId);
    }
}
