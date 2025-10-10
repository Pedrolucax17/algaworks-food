package com.algafoods.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaRegistroDTO {
	@NotNull
	private Long id;
	@NotBlank
	private String nome;
}
