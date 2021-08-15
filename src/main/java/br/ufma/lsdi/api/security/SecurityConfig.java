package br.ufma.lsdi.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;

import br.ufma.lsdi.api.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) //Habiltando a segurança por método
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {	
		httpSecurity.authorizeRequests()
					.anyRequest()
					.authenticated()
					.and()
					.httpBasic()
					.and()
					.csrf().disable();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
		
//		auth.inMemoryAuthentication()
//			.passwordEncoder(encoder)
//			.withUser("user")
//			.password(encoder.encode("user"))
//			.roles("USER")
//			.and()
//			.withUser("admin")
//			.password(encoder.encode("admin"))
//			.roles("USER", "ADMIN");
	}
}
