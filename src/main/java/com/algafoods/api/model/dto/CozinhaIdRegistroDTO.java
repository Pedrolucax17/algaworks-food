package com.algafoods.api.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaIdRegistroDTO {
	@NotNull
	private Long id;
}
