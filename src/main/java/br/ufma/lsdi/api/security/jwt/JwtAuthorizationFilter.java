package br.ufma.lsdi.api.security.jwt;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.ufma.lsdi.api.users.UserService;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	private UserDetailsService userDetailsService;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		String token = request.getHeader("Authorization");
		
		if(StringUtils.isEmpty(token) || !token.startsWith("Bearer")) {
			//Não informou o authorization
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			//Verificando se o token é válido
			if(!JwtUtil.isTokenValid(token)) {
				throw new AccessDeniedException("Acesso Negado");
			}
			
			String login = JwtUtil.getLogin(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(login);
			List<GrantedAuthority> authorities = JwtUtil.getRoles(token);
			
			Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
			
			//Salvar o Authentication no contexto do Spring
			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);
		} catch(RuntimeException e) {
			throw e;
		}
	}
}
