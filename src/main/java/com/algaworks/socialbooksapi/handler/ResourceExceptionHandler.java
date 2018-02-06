package com.algaworks.socialbooksapi.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.socialbooksapi.domain.DetalhesErro;
import com.algaworks.socialbooksapi.services.exceptions.AutorExistenteException;
import com.algaworks.socialbooksapi.services.exceptions.AutorNaoEncontradoException;
import com.algaworks.socialbooksapi.services.exceptions.LivroNaoEncontradoException;

/*Essa anotacao permite interceptar excecoes lancadas pelo Spring*/
@ControllerAdvice
public class ResourceExceptionHandler {

	/*
	 * Essa anotacao indica que quando uma excecao desse tipo ocorrer
	 * esse metodo sera executado, o resource Livro lanca essa excecao em 
	 * varios metodos, e todas as excecoes caem aqui
	 * 
	 * PODERIAMOS CRIAR UM UNICO EXCECAO PARA TODOS OS 404
	 */
	@ExceptionHandler(LivroNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleLivroNaoEncontradoException(LivroNaoEncontradoException e,
			HttpServletRequest request) {

		//Detalhes erro eh uma tratamento opcional
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(404l);
		erro.setTitulo(e.getMessage());
		//link para documentacao do erro da nossa API
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/404");
		erro.setTimestamp(System.currentTimeMillis());

		//com isso, além de retornar o HEADER 404, retorna um json com 
		//mais informacoes uteis
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(AutorExistenteException.class)
	public ResponseEntity<DetalhesErro> handleAutorExistenteException(AutorExistenteException e,
			HttpServletRequest request) {

		//Detalhes erro eh uma tratamento opcional
		DetalhesErro erro = new DetalhesErro();
		//erro 409 eh para conflito
		erro.setStatus(409l);
		erro.setTitulo("Autor já existente.");
		//link para documentacao do erro da nossa API
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/409");
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(AutorNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleAutorNaoEncontradoException(AutorNaoEncontradoException e,
			HttpServletRequest request) {

		//Detalhes erro eh uma tratamento opcional
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(404l);
		erro.setTitulo(e.getMessage());
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/404");
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DetalhesErro> handleDataIntegrityViolationException ( DataIntegrityViolationException e,
			HttpServletRequest request) {

		//Detalhes erro eh uma tratamento opcional
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(400l);
		erro.setTitulo("Violação na integridade dos dados, você está tentando salvar um recurso que não existe!");
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/404");
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
