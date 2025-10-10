package com.algafoods.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafoods.api.assembler.CozinhaInputDisassembler;
import com.algafoods.api.assembler.CozinhaModelAssembler;
import com.algafoods.api.model.dto.CozinhaExibicaoDTO;
import com.algafoods.api.model.dto.CozinhaRegistroDTO;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.repository.CozinhaRepository;
import com.algafoods.domain.service.CozinhaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaDisassembler;
	
	@Autowired
	private CozinhaModelAssembler cozinhaAssembler;

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<CozinhaExibicaoDTO> listar() {
		return cozinhaAssembler.toCollectList(cozinhaRepository.findAll());
	}

	@GetMapping("/{id}")
	public CozinhaExibicaoDTO buscar(@PathVariable Long id) {
		Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
		return cozinhaAssembler.toModel(cozinha);
		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaExibicaoDTO salvar(@RequestBody @Valid CozinhaRegistroDTO cozinhaSalva) {
		Cozinha cozinha = cozinhaDisassembler.toDomainModel(cozinhaSalva);
		return cozinhaAssembler.toModel(cozinhaRepository.save(cozinha));
	}

	@PutMapping("/{id}")
	public CozinhaExibicaoDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaRegistroDTO cozinha) {
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
		
		cozinhaDisassembler.copyToDomainObject(cozinha, cozinhaAtual);

		return cozinhaAssembler.toModel(cozinhaService.salvar(cozinhaAtual));

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cozinhaService.remover(id);
	}

}
