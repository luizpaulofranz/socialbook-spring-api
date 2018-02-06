package com.algaworks.socialbooksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.socialbooksapi.domain.Livro;

/*assim que usamos a repository padrao do spring*/
public interface LivrosRepository extends JpaRepository<Livro, Long>{

}