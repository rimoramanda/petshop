package com.amanda.petshop.service;

import com.amanda.petshop.dto.UsuarioDTO;
import com.amanda.petshop.entity.Perfil;
import com.amanda.petshop.entity.Usuario;
import com.amanda.petshop.mapper.UsuarioMapper;
import com.amanda.petshop.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarTodos() {
        Usuario usuario1 = new Usuario();
        usuario1.setCpf("12345678900");
        usuario1.setNome("Ricardo Alves");
        usuario1.setPerfil(Perfil.ADMIN);
        usuario1.setSenha("SenhaBoa");

        Usuario usuario2 = new Usuario();
        usuario2.setCpf("09876543211");
        usuario2.setNome("Leticia Lourdes");
        usuario2.setPerfil(Perfil.CLIENTE);
        usuario2.setSenha("SenhaOtima");

        UsuarioDTO usuarioDTO1 = new UsuarioDTO();
        usuarioDTO1.setCpf("12345678900");
        usuarioDTO1.setNome("Ricardo Alves");
        usuarioDTO1.setPerfil(Perfil.ADMIN);

        UsuarioDTO usuarioDTO2 = new UsuarioDTO();
        usuarioDTO2.setCpf("09876543211");
        usuarioDTO2.setNome("Leticia Lourdes");
        usuarioDTO2.setPerfil(Perfil.CLIENTE);

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario1, usuario2));
        when(usuarioMapper.toDTO(usuario1)).thenReturn(usuarioDTO1);
        when(usuarioMapper.toDTO(usuario2)).thenReturn(usuarioDTO2);

        List<UsuarioDTO> usuariosDTO = usuarioService.listarTodos();

        assertEquals(2, usuariosDTO.size());
        assertEquals("12345678900", usuariosDTO.get(0).getCpf());
        assertEquals("Ricardo Alves", usuariosDTO.get(0).getNome());
        assertEquals("09876543211", usuariosDTO.get(1).getCpf());
        assertEquals("Leticia Lourdes", usuariosDTO.get(1).getNome());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorCpf() {
        String cpf = "12345678900";
        Usuario usuario = new Usuario();
        usuario.setCpf(cpf);
        usuario.setNome("Amanda Eu");
        usuario.setPerfil(Perfil.ADMIN);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf(cpf);
        usuarioDTO.setNome("Amanda Eu");
        usuarioDTO.setPerfil(Perfil.ADMIN);

        when(usuarioRepository.findByCpf(cpf)).thenReturn((usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.buscarPorCpf(cpf);

        assertEquals("Amanda Eu", result.getNome());
        assertEquals(cpf, result.getCpf());
        verify(usuarioRepository, times(1)).findByCpf(cpf);
    }

    @Test
    void testBuscarPorCpfUsuarioNaoEncontrado() {
        String cpf = "12345678900";

        when(usuarioRepository.findByCpf(cpf)).thenReturn(null);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usuarioService.buscarPorCpf(cpf));
        assertEquals("Usuário não encontrado", thrown.getMessage());
        verify(usuarioRepository, times(1)).findByCpf(cpf);
    }

    @Test
    void testSalvar() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("12345678900");
        usuarioDTO.setNome("Cristiane");
        usuarioDTO.setPerfil(Perfil.ADMIN);

        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");
        usuario.setNome("Cristiane");
        usuario.setPerfil(Perfil.ADMIN);
        usuario.setSenha("minhaSenha");

        when(usuarioMapper.toEntity(usuarioDTO)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.salvar(usuarioDTO);

        assertEquals("12345678900", result.getCpf());
        assertEquals("Cristiane", result.getNome());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testDeletar() {
        String cpf = "12345678900";

        when(usuarioRepository.findByCpf(cpf)).thenReturn((new Usuario()));

        doNothing().when(usuarioRepository).deleteById(cpf);

        usuarioService.deletar(cpf);

        verify(usuarioRepository, times(1)).deleteByCpf(cpf);
    }

    @Test
    void testDeletarUsuarioNaoEncontrado() {
        String cpf = "12345678900";

        when(usuarioRepository.findByCpf(cpf)).thenReturn(null);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usuarioService.deletar(cpf));
        assertEquals("Usuário não encontrado", thrown.getMessage());
        verify(usuarioRepository, times(0)).deleteByCpf(cpf);
    }
}
