package com.algafoods.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	NEGOCIO_EXCEPTION("/erro-negocio", "Violação de regra de negócio"), 
	MENSAGEM_INCOMPREEENSIVEL("/mensagem-incompreensivel", "Mensagem Incompreensivel"), 
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro de URL inválido");

	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}

}
