package com.amanda.petshop.dto;

import com.amanda.petshop.entity.Perfil;

public record RegisterDTO(String cpf, String nome, Perfil perfil,String senha) {
}
