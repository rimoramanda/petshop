package com.amanda.petshop.repository;

import com.amanda.petshop.entity.Raca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacaRepository extends JpaRepository<Raca, Long> {

    List<Raca> findByDescricao(String descricao);
}
