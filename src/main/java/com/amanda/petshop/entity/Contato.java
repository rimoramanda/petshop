package com.amanda.petshop.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "contatos")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag")
    private String tag;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "valor")
    private String valor;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    public Contato(){

    }

    public Contato(Long id, String tag, String tipo, String valor, Cliente cliente) {
        this.id = id;
        this.tag = tag;
        this.tipo = tipo;
        this.valor = valor;
        this.cliente = cliente;
    }
}
