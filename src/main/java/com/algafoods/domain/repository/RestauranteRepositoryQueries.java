package com.algafoods.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algafoods.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

}