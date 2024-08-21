package com.amanda.petshop.service;

import com.amanda.petshop.dto.UsuarioDTO;
import com.amanda.petshop.entity.Perfil;
import com.amanda.petshop.entity.Usuario;
import com.amanda.petshop.mapper.UsuarioMapper;
import com.amanda.petshop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    public UsuarioDTO buscarPorCpf(String cpf) {
        Usuario usuario = (Usuario) usuarioRepository.findByCpf(cpf);
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    public UsuarioDTO atualizar(String cpf, UsuarioDTO usuarioDTO) {
        Usuario usuario = (Usuario) usuarioRepository.findByCpf(cpf);
        usuario.setNome(usuarioDTO.getNome());
        usuario.setPerfil(Perfil.valueOf(String.valueOf(usuarioDTO.getPerfil())));
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(updatedUsuario);
    }

    public void deletar(String cpf) {
        Usuario usuario = (Usuario) usuarioRepository.findByCpf(cpf);

        usuarioRepository.deleteByCpf(cpf);
    }
}
