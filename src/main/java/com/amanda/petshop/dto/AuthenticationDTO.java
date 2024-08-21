package com.amanda.petshop.dto;


import com.amanda.petshop.entity.Perfil;

public record AuthenticationDTO(String cpf, String nome, Perfil perfil, String senha) {

}
