package com.algafoods.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Entidade com id %d está em uso";

	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe um cadastro com o Id: %d";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void remover(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));		
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
		}
	}
	
	
	public Cozinha buscarOuFalhar(Long id) {
		return cozinhaRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id)));
	}
	
	
}
