package com.algaworks.socialbooksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.socialbooksapi.domain.Comentario;

/*assim que usamos a repository padrao do spring*/
public interface ComentariosRepository extends JpaRepository<Comentario, Long>{

}