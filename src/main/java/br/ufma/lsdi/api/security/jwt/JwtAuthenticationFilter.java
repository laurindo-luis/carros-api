package br.ufma.lsdi.api.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufma.lsdi.api.users.User;
import br.ufma.lsdi.api.users.UserDTO;

//Classe que controla o processo de login e geração do Token
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final String AUTH_URL = "/api/v1/login";
	private final AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl(AUTH_URL);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			JwtLoginInput login = new ObjectMapper().readValue(request.getInputStream(), JwtLoginInput.class);
					
			if(StringUtils.isEmpty(login.getUsername()) || StringUtils.isEmpty(login.getPassword())) {
				throw new BadCredentialsException("Invalid username/password");
			}
			
			Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
			return authenticationManager.authenticate(auth);		
		} catch(IOException e) {
			throw new BadCredentialsException(e.getMessage());
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
		
		User user = (User) authentication.getPrincipal();
		user.setSenha(null);
		String token = JwtUtil.createToken(user);
		String json = UserDTO.create(user, token).toJson();
		ServletUtil.write(response, HttpStatus.OK, json);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		String json = ServletUtil.toJson("error", "Login incorreto");
		ServletUtil.write(response, HttpStatus.UNAUTHORIZED, json);
	}
}
