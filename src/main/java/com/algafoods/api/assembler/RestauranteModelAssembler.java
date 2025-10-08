package com.algafoods.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.CozinhaExibicaoResDTO;
import com.algafoods.api.model.dto.RestauranteExibicaoDTO;
import com.algafoods.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	
	public RestauranteExibicaoDTO toModel(Restaurante restauranteSalvo) {
		RestauranteExibicaoDTO restauranteExibicao = new RestauranteExibicaoDTO();
		restauranteExibicao.setId(restauranteSalvo.getId());
		restauranteExibicao.setNome(restauranteSalvo.getNome());
		restauranteExibicao.setTaxaFrete(restauranteSalvo.getTaxaFrete());
		CozinhaExibicaoResDTO cozinha = new CozinhaExibicaoResDTO();
		cozinha.setId(restauranteSalvo.getCozinha().getId());
		cozinha.setNome(restauranteSalvo.getCozinha().getNome());
		restauranteExibicao.setCozinha(cozinha);
		return restauranteExibicao;
	}
	
	public List<RestauranteExibicaoDTO> toCollectList(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}
