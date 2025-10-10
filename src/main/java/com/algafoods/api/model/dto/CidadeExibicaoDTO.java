package com.algafoods.api.model.dto;

import com.algafoods.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeExibicaoDTO {
	private Long id;
	private String nome;		
	private Estado estado;
}
