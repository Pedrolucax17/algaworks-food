package com.algafoods.domain.exception;

public class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException{
	
	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public RestauranteNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de estado com código %d", id));
	}

}
