package com.algaworks.socialbooksapi.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class Livro {

	/*
	 * essa anotacao controla para a API nao enviar campos nulos
	 * Essas anotacoes permitem formatar as saidas 
	 */
	
	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//se eh NotEmpty, nao precisa do @JsonInclude(Include.NON_NULL)  
	@NotEmpty(message = "O campo título não pode ser vazio.")
	@JsonProperty("titulo")
	private String nome;
	
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(pattern="dd/MM/yyyy")
	@NotNull(message = "O campo publicação é de preenchimento obrigatório.")
	private Date publicacao;
	
	@JsonInclude(Include.NON_NULL)
	@NotNull(message = "O campo editora é de preenchimento obrigatório.")
	private String editora;
	
	@JsonInclude(Include.NON_NULL)
	@Size(max=1500, message = "O resumo não pode conter mais de 1500 caracteres.")
	private String resumo;
	
	@JsonInclude(Include.NON_EMPTY)
	//@Transient evita que o JPA faca a relacao entre livro e comentario
	@OneToMany(mappedBy = "livro",fetch = FetchType.LAZY)
	private List<Comentario> comentarios;
	
	@ManyToOne
	@JoinColumn(name = "idAutor")
	@JsonInclude(Include.NON_NULL)
	private Autor autor;
	
	public Livro() {}
	
	public Livro(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Date publicacao) {
		this.publicacao = publicacao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}