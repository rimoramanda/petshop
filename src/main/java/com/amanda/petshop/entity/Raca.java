package com.amanda.petshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "racas")
public class Raca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    public Raca(){

    }
    public Raca(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
}
