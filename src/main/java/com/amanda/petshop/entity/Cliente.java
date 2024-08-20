package com.amanda.petshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "clientes")
public class Cliente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "data_de_cadastro", nullable = false)
    private LocalDate dataDeCadastro;


    @ManyToOne
    @JoinColumn(name = "usuario_cpf", referencedColumnName = "cpf", nullable = true)
    private Usuario usuario;

    public Cliente(){

    }

    public Cliente(Long id, String cpf, String nome, LocalDate dataDeCadastro, Usuario usuario) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.dataDeCadastro = dataDeCadastro;
        this.usuario = usuario;
    }

}
