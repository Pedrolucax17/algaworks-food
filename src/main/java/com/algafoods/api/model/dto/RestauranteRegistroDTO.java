package com.algafoods.api.model.dto;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRegistroDTO {
	
	@NotBlank
	private String nome;
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	@Valid
	@NotNull
	private CozinhaIdRegistroDTO cozinhaId;
	
}
