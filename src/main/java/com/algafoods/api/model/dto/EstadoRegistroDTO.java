package com.algafoods.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoRegistroDTO {
	@NotBlank
	private String nome;
}
