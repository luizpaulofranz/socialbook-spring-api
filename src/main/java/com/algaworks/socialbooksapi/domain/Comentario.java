package com.algaworks.socialbooksapi.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
//jackson eh a dependencia q cuida dos JSONS
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="O comentário deve ser preenchido.")
	@Size(max = 1500, message = "Deve conter no máximo 1500 caracteres.")
	//vamos mudar o nome desse campo no Json para "comentario"
	@JsonProperty("comentario")
	private String texto;
	
	@JsonInclude(Include.NON_NULL)
	private String usuario;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonInclude(Include.NON_NULL)
	private Date data;
	
	//muitos comentarios para um livro
	@ManyToOne(fetch = FetchType.LAZY)
	//qual a coluna responsavel pela ligacao
	@JoinColumn(name = "idLivro")
	//o livro tem um comentario, que tem um livro, que tem um comentario, que tem um livro ...
	//isso eh para evitar um loop infinito
	@JsonIgnore
	private Livro livro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
}
