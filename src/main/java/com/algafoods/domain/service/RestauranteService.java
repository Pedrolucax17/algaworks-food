package com.algafoods.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.model.Restaurante;
import com.algafoods.domain.repository.CozinhaRepository;
import com.algafoods.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	
	public Restaurante salvar(Restaurante restaurante) {
		
		Cozinha cozinha = cozinhaRepository.buscar(restaurante.getCozinha().getId());
		
		if(cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha com id %d não existe", restaurante.getCozinha().getId())
			);
		}
		
		return restauranteRepository.salvar(restaurante);
	}
	
}
