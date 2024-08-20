package com.amanda.petshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "perfil", nullable = false)
    private String perfil; // Pode ser "Cliente" ou "Admin"

    @Column(name = "senha", nullable = false)
    private String senha;

    public Usuario() {

    }

    public Usuario(String cpf, String nome, String perfil, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.perfil = perfil;
        this.senha = senha;
    }

}

