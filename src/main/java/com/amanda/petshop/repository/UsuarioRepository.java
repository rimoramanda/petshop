package com.amanda.petshop.repository;


import com.amanda.petshop.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByCpf(String cpf);

    void deleteByCpf(String cpf);
}
