package com.amanda.petshop.controller;

import com.amanda.petshop.dto.UsuarioDTO;
import com.amanda.petshop.entity.Perfil;
import com.amanda.petshop.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void testListarUsuarios() throws Exception {
        UsuarioDTO usuarioDTO1 = new UsuarioDTO();
        usuarioDTO1.setCpf("12345678900");
        usuarioDTO1.setNome("Maria Heloisa");
        usuarioDTO1.setPerfil(Perfil.ADMIN);

        UsuarioDTO usuarioDTO2 = new UsuarioDTO();
        usuarioDTO2.setCpf("09876543211");
        usuarioDTO2.setNome("Ricardo Alves");
        usuarioDTO2.setPerfil(Perfil.CLIENTE);

        when(usuarioService.listarTodos()).thenReturn(List.of(usuarioDTO1, usuarioDTO2));

        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cpf").value("12345678900"))
                .andExpect(jsonPath("$[0].nome").value("Maria Heloisa"))
                .andExpect(jsonPath("$[0].perfil").value(Perfil.ADMIN))
                .andExpect(jsonPath("$[1].cpf").value("09876543211"))
                .andExpect(jsonPath("$[1].nome").value("Ricardo Alves"))
                .andExpect(jsonPath("$[1].perfil").value(Perfil.CLIENTE));

        verify(usuarioService, times(1)).listarTodos();
    }

    @Test
    void testBuscarUsuarioPorCpf() throws Exception {
        String cpf = "12345678900";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf(cpf);
        usuarioDTO.setNome("Maria Heloisa2");
        usuarioDTO.setPerfil(Perfil.ADMIN);

        when(usuarioService.buscarPorCpf(cpf)).thenReturn(usuarioDTO);

        mockMvc.perform(get("/usuarios/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.nome").value("Maria Heloisa2"))
                .andExpect(jsonPath("$.perfil").value(Perfil.ADMIN));

        verify(usuarioService, times(1)).buscarPorCpf(cpf);
    }

    @Test
    void testSalvarUsuario() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678666");
        usuarioDTO.setNome("Livia");
        usuarioDTO.setPerfil(Perfil.ADMIN);

        when(usuarioService.salvar(any(UsuarioDTO.class))).thenReturn(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cpf\":\"12345678666\", \"nome\":\"Livia\", \"perfil\":\"admin\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value("12345678666"))
                .andExpect(jsonPath("$.nome").value("Livia"))
                .andExpect(jsonPath("$.perfil").value(Perfil.ADMIN));

        verify(usuarioService, times(1)).salvar(any(UsuarioDTO.class));
    }

    @Test
    void testAtualizarUsuario() throws Exception {
        String cpf = "33345678900";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf(cpf);
        usuarioDTO.setNome("Usuário Atualizado");
        usuarioDTO.setPerfil(Perfil.CLIENTE);

        when(usuarioService.atualizar(eq(cpf), any(UsuarioDTO.class))).thenReturn(usuarioDTO);

        mockMvc.perform(put("/usuarios/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cpf\":\"33345678900\", \"nome\":\"Usuário Atualizado\", \"perfil\":\"cliente\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.nome").value("Usuário Atualizado"))
                .andExpect(jsonPath("$.perfil").value( Perfil.CLIENTE));

        verify(usuarioService, times(1)).atualizar(eq(cpf), any(UsuarioDTO.class));
    }


    @Test
    void testDeletarUsuario() throws Exception {
        String cpf = "33345678900";

        mockMvc.perform(delete("/usuarios/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).deletar(cpf);
    }
}
