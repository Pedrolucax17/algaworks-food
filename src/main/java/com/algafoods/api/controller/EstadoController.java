package com.algafoods.api.controller;

import java.util.List;
import java.util.Optional;

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

import com.algafoods.api.assembler.EstadoInputDisassembler;
import com.algafoods.api.assembler.EstadoModelAssembler;
import com.algafoods.api.model.dto.EstadoExibicaoDTO;
import com.algafoods.api.model.dto.EstadoRegistroDTO;
import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.model.Estado;
import com.algafoods.domain.repository.EstadoRepository;
import com.algafoods.domain.service.EstadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private EstadoInputDisassembler estadoDisassembler;
	
	@Autowired
	private EstadoModelAssembler estadoAssembler;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<EstadoExibicaoDTO> listar() {
		return estadoAssembler.toCollectList(estadoRepository.findAll());
	}

	@GetMapping("/{id}")
	public EstadoExibicaoDTO buscar(@PathVariable Long id) {
		Estado estadoSalvo = estadoService.buscarOuFalhar(id);
		return estadoAssembler.toModel(estadoSalvo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoExibicaoDTO salvar(@RequestBody @Valid EstadoRegistroDTO estadoInput) {
		Estado estadoSalvo = estadoDisassembler.toDomainModel(estadoInput); 
		return estadoAssembler.toModel(estadoService.salvar(estadoSalvo));
	}

	@PutMapping("/{id}")
	public EstadoExibicaoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoRegistroDTO estadoInput) {
		Estado estadoAtual = estadoService.buscarOuFalhar(id);

		estadoDisassembler.copyToDomainObject(estadoInput, estadoAtual);

		return estadoAssembler.toModel(estadoService.salvar(estadoAtual));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> remover(@PathVariable Long id) {
		try {
			Optional<Estado> estadoOptional = estadoRepository.findById(id);

			if (estadoOptional.isPresent()) {
				estadoService.remover(id);
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
