package com.algaworks.socialbooksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.socialbooksapi.domain.Autor;

/*assim que usamos a repository padrao do spring*/
public interface AutoresRepository extends JpaRepository<Autor, Long>{

}