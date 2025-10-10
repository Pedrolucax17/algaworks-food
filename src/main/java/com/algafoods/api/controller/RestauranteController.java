package com.algafoods.api.controller;

import static com.algafoods.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algafoods.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.List;

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

import com.algafoods.api.assembler.RestauranteInputDisassembler;
import com.algafoods.api.assembler.RestauranteModelAssembler;
import com.algafoods.api.model.dto.RestauranteExibicaoDTO;
import com.algafoods.api.model.dto.RestauranteRegistroDTO;
import com.algafoods.domain.exception.CozinhaNaoEncontradaException;
import com.algafoods.domain.exception.EntidadeEmUsoException;
import com.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algafoods.domain.exception.NegocioException;
import com.algafoods.domain.model.Restaurante;
import com.algafoods.domain.repository.RestauranteRepository;
import com.algafoods.domain.service.RestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RestauranteModelAssembler restAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restDisassembler;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<RestauranteExibicaoDTO> listar() {
		List<RestauranteExibicaoDTO> listRestauranteDto = restAssembler.toCollectList(restauranteRepository.findAll());
		return listRestauranteDto;
	}

	@GetMapping("/{id}")
	public RestauranteExibicaoDTO buscar(@PathVariable Long id) {
		Restaurante restauranteSalvo = restauranteService.buscarOuFalhar(id);
		
		RestauranteExibicaoDTO restauranteExibicao = restAssembler.toModel(restauranteSalvo);
		
		return restauranteExibicao;
		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteExibicaoDTO salvar(@RequestBody @Valid RestauranteRegistroDTO restauranteSalvo) {
		try {
			Restaurante restaurante = restDisassembler.toDomainModel(restauranteSalvo);
			return restAssembler.toModel(restauranteService.salvar(restaurante));		
		}
		catch(CozinhaNaoEncontradaException e){
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public RestauranteExibicaoDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteRegistroDTO restauranteSalvo) {
		
		try {
			Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);
			
			restDisassembler.copyToDomainObject(restauranteSalvo, restauranteAtual);
			
			return restAssembler.toModel(restauranteService.salvar(restauranteAtual));
		}catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestauranteExibicaoDTO> remover(@PathVariable Long id) {
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
	public List<RestauranteExibicaoDTO> restaurantePorNomeFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restAssembler.toCollectList(restauranteRepository.find(nome, taxaInicial, taxaFinal));
	}

	@GetMapping("/com-frete-gratis")
	public List<RestauranteExibicaoDTO> restaurantesComFreteGratis(String nome) {
		return restAssembler.toCollectList(restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome))));
	}

}
