package com.example.springbootapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	// Pour crypter les mots de passe
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	public NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
	// Cree l'authentification a partir des informations (User -> Role
	// (Authorities)) +
	// utilise l 'algorithme d'encryptage
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	// Dans la methode configure, on definit l’acces a notre application
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// demande a toutes les requetes de s’authentifier
//		http.cors().and().authorizeRequests().anyRequest().fullyAuthenticated();
//		// httpBasic() est utilisée pour permettre à la fonctionnalité 
//		// d'authentifier l'utilisateur lors de la requête HTTP.
//		http.httpBasic();
//		http.csrf().disable();
		
		http.authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .httpBasic();
	}
}
