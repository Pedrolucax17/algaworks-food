package com.algafoods.api.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeRegistroDTO {
	
	@NotBlank
	private String nome;		
	
	@Valid
	@NotNull
	private EstadoIdRegistroDTO estadoId;
}
