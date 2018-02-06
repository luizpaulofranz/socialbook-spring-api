package com.algaworks.socialbooksapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooksapi.domain.Autor;
import com.algaworks.socialbooksapi.repository.AutoresRepository;
import com.algaworks.socialbooksapi.services.exceptions.AutorExistenteException;
import com.algaworks.socialbooksapi.services.exceptions.AutorNaoEncontradoException;

@Service
public class AutoresService {
	
	@Autowired
	private AutoresRepository autoresRepository;
	
	public List<Autor> listar(){
		return autoresRepository.findAll();
	}
	
	public Autor salvar(Autor autor) throws AutorExistenteException{
		if(autor.getId() != null){
			//fazemos assim para nao lanca uma excecao quando nao encontrar o autor pelo id
			Autor a = autoresRepository.findOne(autor.getId());
			if(a != null){
				throw new AutorExistenteException("O autor já existe.");
			}
		}
		return autoresRepository.save(autor);
	}
	
	public Autor buscar(Long id){ 
		Autor autor = autoresRepository.findOne(id);
		if(autor == null){
			throw new AutorNaoEncontradoException("O autor não pode ser encontrado!");
		}
		return autor;
	}

}
