package com.algafoods.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.exception.RestauranteNaoEncontradaException;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.model.Restaurante;
import com.algafoods.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	private static final String MSG_ENTIDADE_EM_USO = "Restaurante com o id %d está em uso";

	private static final String MSG_ENTIDADE_NAO_ENCONTRADA = "Restaurante com o id %d não encontrada";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	
	
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
	
	public void remover(Long id) {
		try {
			restauranteRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradaException(
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
				() -> new RestauranteNaoEncontradaException(String.format(MSG_ENTIDADE_NAO_ENCONTRADA, id))
		);
	}
	
}
