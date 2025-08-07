package com.algafoods.api.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteExibicaoDTO {
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaExibicaoResDTO cozinha;
}
