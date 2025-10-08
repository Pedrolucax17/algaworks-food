package com.algafoods.api.assembler;

import org.springframework.stereotype.Component;

import com.algafoods.api.model.dto.RestauranteRegistroDTO;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	public Restaurante toDomainModel(RestauranteRegistroDTO restauranteRegistro) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteRegistro.getNome());
		restaurante.setTaxaFrete(restauranteRegistro.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteRegistro.getCozinhaId().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
}
