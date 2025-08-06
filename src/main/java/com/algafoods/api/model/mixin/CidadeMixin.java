package com.algafoods.api.model.mixin;

import com.algafoods.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
}
