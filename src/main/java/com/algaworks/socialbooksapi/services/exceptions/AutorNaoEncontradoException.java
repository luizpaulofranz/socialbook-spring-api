package com.algaworks.socialbooksapi.services.exceptions;

public class AutorNaoEncontradoException extends RuntimeException{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6288077569874509061L;

	public AutorNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public AutorNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
}
}
