package com.algaworks.socialbooksapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//habilita esse arquivo de configuracao dentro do Spring
@EnableWebSecurity
// precisa estender essa classe para configurar o SpringSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/*### PRECISA TER ESSES DOIS MÉTODOS ###*/
	
	@Autowired
	// configura qual vai ser o mecanismo de autenticacao, pode ser
	// em memoria, com banco, etc, nesse caso, em memoria
	//responsavel por controlar o "login"
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// aqui configuramos a autenticacao
		auth.inMemoryAuthentication().withUser("spring").password("security").roles("USER");
	}

	//Esse metodo configura o acesso das requests, quais precisam de autenticacao
	//qual nao precisa, etc
	protected void configure(HttpSecurity http) throws Exception {
		//configuramos os acessos das requisicoes
		http.authorizeRequests()
			//aqui configuramos uma URI que nao precisa de autenticacao
			.antMatchers("/h2-console/**")
			.permitAll()
			//para permitir o Cross Origns CORS para acessar pelo html
			//e por qualquer tipo de cliente
			.antMatchers(HttpMethod.OPTIONS)
			.permitAll()
			//todas as outras URIs precisam de autenticacao
			.anyRequest()
			.authenticated()
			.and()
			//desativamos o CSRF
			.httpBasic().and().csrf().disable();
	}
}
