package com.algaworks.socialbooksapi.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooksapi.domain.Comentario;
import com.algaworks.socialbooksapi.domain.Livro;
import com.algaworks.socialbooksapi.repository.ComentariosRepository;
import com.algaworks.socialbooksapi.repository.LivrosRepository;
import com.algaworks.socialbooksapi.services.exceptions.LivroNaoEncontradoException;

@Service
public class LivrosService {

	@Autowired
	private LivrosRepository livrosRepository;
	
	@Autowired
	//tratamos os comentarios como um service dos livros
	private ComentariosRepository comentariosRepository;

	public List<Livro> listar() {
		return livrosRepository.findAll();
	}

	public Livro buscarById(Long id) throws LivroNaoEncontradoException {
		// metodo padroa do Spring Data JPA
		Livro livro = livrosRepository.findOne(id);
		// se nao existe livro, lanca excecao
		if (livro == null) {
			throw new LivroNaoEncontradoException("O livro não pôde ser encontrado.");
		}

		return livro;
	}

	public Livro salvar(Livro livro) {
		livro.setId(null);
		return livrosRepository.save(livro);
	}

	public void deletar(Long id) throws LivroNaoEncontradoException {
		// se nao existe o livro, lanca excecao
		try {
			livrosRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new LivroNaoEncontradoException("O livro não pôde ser encontrado.");
		}
	}

	public void update(Livro livro) {
		verificarExistencia(livro);
		livrosRepository.save(livro);
	}

	// esse metodo eh usado para evitar que alguem faca update em um livro q nao
	// existe
	private void verificarExistencia(Livro livro) {
		buscarById(livro.getId());
	}

	/* #################################################	 */
	/* ### SUB - RECURSO COMENTARIOS ###################	 */
	/* #################################################	 */
	
	public Comentario salvarComentario(Long livroId, Comentario comentario) {
		Livro livro = buscarById(livroId);

		comentario.setLivro(livro);
		comentario.setData(new Date());

		return comentariosRepository.save(comentario);
	}
	
	public List<Comentario> listarComentarios(Long idLivro) {
		//importante chamar esse busca, pois ele lanca excecoes
		Livro livro = buscarById(idLivro);
		return livro.getComentarios();
	}

}
