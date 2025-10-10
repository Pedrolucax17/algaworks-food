package com.algafoods.api.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdRegistroDTO {
	@NotNull
	private Long id;
}
