package com.algafoods.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafoods.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
	
}
