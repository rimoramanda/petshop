package com.amanda.petshop.entity;

public enum Perfil {
    ADMIN("admin"),
    CLIENTE("cliente");


    private String perfil;

    Perfil(String perfil){
        this.perfil=perfil;
    }

    public String getPerfil() {
        return perfil;
    }
}
