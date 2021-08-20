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

import br.ufma.lsdi.api.security.jwt.JwtAuthenticationFilter;
import br.ufma.lsdi.api.security.jwt.JwtAuthorizationFilter;
import br.ufma.lsdi.api.security.jwt.handler.AccessDeniedHandler;
import br.ufma.lsdi.api.security.jwt.handler.UnauthorizedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) //Habiltando a segurança por método
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private UnauthorizedHandler unauthorizedHandler;
	
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {	
		AuthenticationManager authenticationManager = authenticationManager();
		httpSecurity.authorizeRequests()
					.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
					.antMatchers(HttpMethod.GET, "/api/v1/login").permitAll()
					.antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
					.anyRequest().authenticated()
					.and().csrf().disable()
					.addFilter(new JwtAuthenticationFilter(authenticationManager))
					.addFilter(new JwtAuthorizationFilter(authenticationManager, userDetailsService))
					.exceptionHandling()
					.accessDeniedHandler(accessDeniedHandler)
					.authenticationEntryPoint(unauthorizedHandler)
					.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
