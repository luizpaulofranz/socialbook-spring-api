/*
 TODAS AS EXCECOES DESSA CLASSE SAO TRATADAS EM:
 ResourceExceptionHandler
 UTILIZAMOS O Exceptioin Handler para controlar nossas Excecoes
 */

package com.algaworks.socialbooksapi.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooksapi.domain.Comentario;
import com.algaworks.socialbooksapi.domain.Livro;
import com.algaworks.socialbooksapi.services.LivrosService;

@RestController // informamos que isso eh um resource
@RequestMapping("/livros") // indica a URL
public class LivrosResources {

	// Utilizacao de um service eh recomendacao do DDD
	@Autowired
	private LivrosService livrosService;

	@RequestMapping(method = RequestMethod.GET, produces ={
		//precisa de uma dependencia pra possibilitar o XML
		//Assim que disponibilizamos a API via Json e XML
		//Pra isso usamos o Accept na requisicao
		MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE
	})
	//para permitir o CORS, acessar de clientes HTML ou de qualuqer tipo
	@CrossOrigin
	// ResponseEntity eh o padrao de responses pra RestFul com Spring
	public ResponseEntity<List<Livro>> listar() {
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());
	}

	@RequestMapping(method = RequestMethod.POST)
	// a anotacao @RequestBody converte a requisicao em um objeto
	//O @Valid executa as validacoes do JPA antes de executar esse resource
	//para validar as entradas em nossa API
	public ResponseEntity<Void> salvar(@Valid @RequestBody Livro livro) {
		// hibernate insere o ID do obj
		livro = livrosService.salvar(livro);

		// aqui tratamos um opadrao de APIs restful, indicando o "location"
		// desse recurso inserido
		// o location vai estar nos headers do response
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	// @PathVariable pega o valor que esta na URL e converte pro tipo
	// configurado
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Livro> buscarById(@PathVariable("id") Long id) {
		Livro livro = null;
		//Definimos o cache dessa requisicao em 30 seg
		//esse cache fica no cliente
		CacheControl cache = CacheControl.maxAge(30, TimeUnit.SECONDS);
		// EXCECAO TRATADA NO ResourceExceptionHandler
		livro = livrosService.buscarById(id);
		// com o ResponseEntity permitenos definir os responses HTTP
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cache).body(livro);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		livrosService.deletar(id);
		// caso contrario retornamos o sucesso 204 indicando que nao ha conteudo
		return ResponseEntity.noContent().build();
	}

	// agora o metodo HTTP eh PUT, para dar um UPDATE
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody Livro livro, @PathVariable("id") Long id) {
		// apenas setando o ID o Hibernate faz o update
		livro.setId(id);
		livrosService.update(livro);
		// noContent adiciona os codigos corretos para recursos que nao retornam
		// nada
		return ResponseEntity.noContent().build();
	}

	/* #################################################	 */
	/* ### SUB - RECURSO COMENTARIOS ###################	 */
	/* #################################################	 */
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
	public ResponseEntity<Void> adicionarComentario(@Valid @PathVariable("id") Long livroId,
			@RequestBody Comentario comentario) {

		//pegar o usuario logado com o SpringSecurity
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		comentario.setUsuario(auth.getName());
		
		livrosService.salvarComentario(livroId, comentario);

		// como a nao ha um GET para um comentario especifico,
		// listamos todos os comentarios de um livro
		// retornamos a URL atual mesmo
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
	//retorna todos os comentarios de um livro
	public ResponseEntity<List<Comentario>> getComentarios(@PathVariable("id") Long livroId) {
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listarComentarios(livroId));
	}

}
