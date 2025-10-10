package com.algafoods.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.RestauranteExibicaoDTO;
import com.algafoods.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public RestauranteExibicaoDTO toModel(Restaurante restauranteSalvo) {
		return mapper.map(restauranteSalvo, RestauranteExibicaoDTO.class);
	}
	
	public List<RestauranteExibicaoDTO> toCollectList(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(r -> toModel(r))
				.collect(Collectors.toList());
	}
}
