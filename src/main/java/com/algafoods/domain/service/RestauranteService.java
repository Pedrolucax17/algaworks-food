package com.algafoods.domain.service;

import java.util.Optional;

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
		
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		
		if(cozinha.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha com id %d não existe", cozinhaId)
			);
		}
		
		restaurante.setCozinha(cozinha.get());
		
		return restauranteRepository.salvar(restaurante);
	}
	
}
