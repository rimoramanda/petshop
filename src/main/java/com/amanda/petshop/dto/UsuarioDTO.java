package com.amanda.petshop.dto;

import com.amanda.petshop.entity.Perfil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private String cpf;
    private String nome;
    private Perfil perfil;
    private String senha;
}

