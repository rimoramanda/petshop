package com.amanda.petshop.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "atendimento")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(name = "descricao_do_atendimento", nullable = false)
    private String descricaoDoAtendimento;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "data", nullable = false)
    private LocalDate data;

     public Atendimento(){

     }
    public Atendimento(Long id, Pet pet, String descricaoDoAtendimento, BigDecimal valor, LocalDate data) {
        this.id = id;
        this.pet = pet;
        this.descricaoDoAtendimento = descricaoDoAtendimento;
        this.valor = valor;
        this.data = data;
    }
}
