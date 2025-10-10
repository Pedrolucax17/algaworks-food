package com.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.RestauranteRegistroDTO;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Restaurante toDomainModel(RestauranteRegistroDTO restauranteRegistro) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteRegistro.getNome());
		restaurante.setTaxaFrete(restauranteRegistro.getTaxaFrete());

		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteRegistro.getCozinhaId().getId());

		restaurante.setCozinha(cozinha);

		return restaurante;
	}

	public void copyToDomainObject(RestauranteRegistroDTO restauranteInput, Restaurante restaurante) {
		restaurante.setCozinha(new Cozinha());
		mapper.map(restauranteInput, restaurante);
	}

}
