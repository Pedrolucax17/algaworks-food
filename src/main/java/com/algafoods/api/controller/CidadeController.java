package com.algafoods.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafoods.api.assembler.CidadeInputDisassembler;
import com.algafoods.api.assembler.CidadeModelAssembler;
import com.algafoods.api.model.dto.CidadeExibicaoDTO;
import com.algafoods.api.model.dto.CidadeRegistroDTO;
import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algafoods.domain.exception.EstadoNaoEncontradaException;
import com.algafoods.domain.exception.NegocioException;
import com.algafoods.domain.model.Cidade;
import com.algafoods.domain.repository.CidadeRepository;
import com.algafoods.domain.service.CidadeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeAssembler;
	
	@Autowired 
	private CidadeInputDisassembler cidadeDisassembler;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CidadeExibicaoDTO> listar() {
		return cidadeAssembler.toCollectList(cidadeRepository.findAll());
	}

	@GetMapping("/{id}")
	public CidadeExibicaoDTO buscar(@PathVariable Long id) {
		Cidade cidade = cidadeService.buscarOuFalhar(id);
		return cidadeAssembler.toModel(cidade);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeExibicaoDTO salvar(@RequestBody @Valid CidadeRegistroDTO cidadeSalva) {
		try {
			Cidade cidade = cidadeDisassembler.toDomainModel(cidadeSalva);
			return cidadeAssembler.toModel(cidadeService.salvar(cidade));
		}
		catch(EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public CidadeExibicaoDTO atualizar(@PathVariable Long id, @RequestBody @Valid CidadeRegistroDTO cidade) {
		
		try {
			Cidade cidadeAtual = cidadeService.buscarOuFalhar(id);

			cidadeDisassembler.copyToDomainObject(cidade, cidadeAtual);
			return cidadeAssembler.toModel(cidadeService.salvar(cidadeAtual));
		}
		catch(EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cidade> remover(@PathVariable Long id) {
		try {
			cidadeService.remover(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
