package com.algaworks.socialbooksapi.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooksapi.domain.Autor;
import com.algaworks.socialbooksapi.services.AutoresService;

@RestController
@RequestMapping("/autores")
public class AutoresResource {

	@Autowired
	private AutoresService autoresService;
		
	@RequestMapping(method = RequestMethod.GET, produces = {
		//precisa de uma dependencia pra possibilitar o XML
		//Assim que disponibilizamos a API via Json e XML
		//Pra isso usamos o Accept na requisicao
		MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE
	})
	public ResponseEntity<List<Autor>> listar(){
		List<Autor> autores = this.autoresService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(autores);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	//O @Valid executa as validacoes do JPA antes de executar esse resource
	public ResponseEntity<Void> salvar(@Valid @RequestBody Autor autor){
		autor = autoresService.salvar(autor);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(autor.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Autor> buscar(@PathVariable Long id)
	{
		Autor autor = autoresService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(autor);
	}
	
}
