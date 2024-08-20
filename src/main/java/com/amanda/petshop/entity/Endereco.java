package com.amanda.petshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "tag")
    private String tag;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;



    public Endereco() {
    }

    public Endereco(Long id, String logradouro, String cidade, String bairro, String complemento, String tag, Cliente cliente) {
        this.id = id;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.bairro = bairro;
        this.complemento = complemento;
        this.tag = tag;
        this.cliente = cliente;
    }
}
