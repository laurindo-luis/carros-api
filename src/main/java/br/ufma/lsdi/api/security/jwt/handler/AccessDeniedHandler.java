package br.ufma.lsdi.api.security.jwt.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.ufma.lsdi.api.security.jwt.ServletUtil;

public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			String json = ServletUtil.toJson("error", "Acesso negado");
			ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
		}
	}
}
