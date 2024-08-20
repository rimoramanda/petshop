package com.amanda.petshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_de_nascimento")
    private LocalDate dataDeNascimento;


    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;


    @ManyToOne
    @JoinColumn(name = "id_raca")
    private Raca raca;

    public Pet() {
    }


    public Pet(Long id, String nome, LocalDate dataDeNascimento, Cliente cliente, Raca raca) {
        this.id = id;
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.cliente = cliente;
        this.raca = raca;
    }
}
