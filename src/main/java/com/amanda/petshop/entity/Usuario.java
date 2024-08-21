package com.amanda.petshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "usuarios")
public class Usuario  implements UserDetails{

    @Id
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private Perfil perfil;

    @Column(name = "senha", nullable = false)
    private String senha;

    public Usuario() {
        this.cpf = cpf;
        this.nome = nome;
        this.perfil = perfil;
        this.senha = senha;

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            if(this.perfil== Perfil.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_CLIENTE"));
                else return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }


    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return cpf;
    }
}

