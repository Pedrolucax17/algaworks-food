package com.algafoods.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{
	
	private static final long serialVersionUID = 1L;
	
	public CozinhaNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CozinhaNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de estado com código %d", id));
	}

}
