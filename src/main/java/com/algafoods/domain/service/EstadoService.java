package com.algafoods.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.exception.EstadoNaoEncontradaException;
import com.algafoods.domain.model.Estado;
import com.algafoods.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private static final String MSG_ENTIDADE_EM_USO = "Estado com id %d está em uso";
	private static final String MSG_ENTIDADE_NAO_ENCONTRADA = "Estado com id %d não existe";
	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void remover(Long id) {
		try {
			estadoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(String.format(MSG_ENTIDADE_NAO_ENCONTRADA, id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ENTIDADE_EM_USO, id));
		}
	}

	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id)
				.orElseThrow(() -> new EstadoNaoEncontradaException(String.format(MSG_ENTIDADE_NAO_ENCONTRADA, id)));

	}

}
