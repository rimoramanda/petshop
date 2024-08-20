package com.amanda.petshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataDeCadastro;
    private String usuarioCpf;

}
