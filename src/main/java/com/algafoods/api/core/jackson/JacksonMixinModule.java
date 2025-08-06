package com.algafoods.api.core.jackson;

import com.algafoods.api.model.mixin.CidadeMixin;
import com.algafoods.api.model.mixin.CozinhaMixin;
import com.algafoods.api.model.mixin.RestauranteMixin;
import com.algafoods.domain.model.Cidade;
import com.algafoods.domain.model.Cozinha;
import com.algafoods.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonMixinModule extends SimpleModule{

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}

}
