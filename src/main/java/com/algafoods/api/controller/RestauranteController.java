package com.algafoods.api.controller;

import static com.algafoods.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algafoods.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
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

import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algafoods.domain.model.Restaurante;
import com.algafoods.domain.repository.RestauranteRepository;
import com.algafoods.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	@GetMapping("/{id}")
	public Restaurante buscar(@PathVariable Long id) {
		return restauranteService.buscarOuFalhar(id);
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {

		Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);

		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "dataCadastro",
				"produtos");

		return restauranteService.salvar(restauranteAtual);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long id) {
		try {
			restauranteRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@GetMapping("/por-nome-e-frete")
	public List<Restaurante> restaurantePorNomeFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}

	@GetMapping("/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {

		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}

}
