package com.algafoods.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algafoods.domain.model.Cidade;
import com.algafoods.domain.model.Estado;
import com.algafoods.domain.repository.CidadeRepository;
import com.algafoods.domain.repository.EstadoRepository;

@Service
public class CidadeService {

	private static final String MSG_ENTIDADE_EM_USO = "Entidade com o id %d está em uso";
	private static final String MSG_ENTIDADE_NAO_ENCONTRADA = "Entidade com o id %d não encontrada";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {

		Long estadoId = cidade.getEstado().getId();

		Optional<Estado> estado = estadoRepository.findById(estadoId);

		if (estado.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha com id %d não existe", estadoId));
		}

		cidade.setEstado(estado.get());

		return cidadeRepository.save(cidade);
	}

	public void remover(Long id) {
		try {
			cidadeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_ENTIDADE_NAO_ENCONTRADA, id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ENTIDADE_EM_USO, id));
		}
	}

	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_ENTIDADE_NAO_ENCONTRADA, id)));
	}

}
