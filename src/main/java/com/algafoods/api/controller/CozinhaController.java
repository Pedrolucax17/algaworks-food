package com.algafoods.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Cozinha> listar(){
		return cozinhaRepository.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		Cozinha cozinha = cozinhaRepository.buscar(id);
		
		if(cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha salvar(@RequestBody Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaRepository.buscar(id);
		
		if(cozinhaAtual != null) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			cozinhaAtual = cozinhaRepository.salvar(cozinhaAtual);
			
			return ResponseEntity.ok(cozinhaAtual);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long id){
		try {
			Cozinha cozinhaAtual = cozinhaRepository.buscar(id);
			
			if(cozinhaAtual != null) {
				cozinhaRepository.remover(cozinhaAtual);
				
				return ResponseEntity.noContent().build();
			}
			
			return ResponseEntity.notFound().build();
		}catch(DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	
}
