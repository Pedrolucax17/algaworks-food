package com.algafoods.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafoods.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
}
