package com.algafoods.domain.exception;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException{
	
	private static final long serialVersionUID = 1L;
	
	public EstadoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public EstadoNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de estado com código %d", id));
	}

}
