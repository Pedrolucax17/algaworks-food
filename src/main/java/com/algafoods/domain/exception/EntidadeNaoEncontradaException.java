package com.algafoods.domain.exception;

public abstract class EntidadeNaoEncontradaException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(String msg) {
		super(msg);
	}

}
