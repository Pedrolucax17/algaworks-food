package com.algafoods.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.model.Restaurante;
import com.algafoods.domain.repository.CozinhaRepository;
import com.algafoods.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	private static final String MSG_ENTIDADE_EM_USO = "Entidade com o id %d está em uso";

	private static final String MSG_ENTIDADE_NAO_ENCONTRADA = "Entidade com o id %d não encontrada";

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
		
		return restauranteRepository.save(restaurante);
	}
	
	public void remover(Long id) {
		try {
			restauranteRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_ENTIDADE_NAO_ENCONTRADA, id)
			);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ENTIDADE_EM_USO, id)
			);
		}
	}
	
	public Restaurante buscarOuFalhar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(MSG_ENTIDADE_NAO_ENCONTRADA, id))
		);
	}
	
}
