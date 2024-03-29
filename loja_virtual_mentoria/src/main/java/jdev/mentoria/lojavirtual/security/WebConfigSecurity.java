package jdev.mentoria.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable().authorizeRequests().antMatchers("/").permitAll();

		
	}
}
