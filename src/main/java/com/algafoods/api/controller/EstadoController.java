package com.algafoods.api.controller;

import java.util.List;
import java.util.Optional;

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

import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.model.Estado;
import com.algafoods.domain.repository.EstadoRepository;
import com.algafoods.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EstadoService estadoService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	@GetMapping("/{id}")
	public Estado buscar(@PathVariable Long id) {
		return estadoService.buscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado salvar(@RequestBody Estado estado) {
		return estadoService.salvar(estado);
	}

	@PutMapping("/{id}")
	public Estado atualizar(@PathVariable Long id, @RequestBody Estado estado) {
		Estado estadoAtual = estadoService.buscarOuFalhar(id);

		BeanUtils.copyProperties(estado, estadoAtual, "id");

		return estadoService.salvar(estadoAtual);
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
