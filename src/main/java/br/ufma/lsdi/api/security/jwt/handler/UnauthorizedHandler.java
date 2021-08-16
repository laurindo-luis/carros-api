package br.ufma.lsdi.api.security.jwt.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import br.ufma.lsdi.api.security.jwt.ServletUtil;

public class UnauthorizedHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		//Comando se token errado ou ausente
		String json = ServletUtil.toJson("error", "Não autorizado");
		ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
	}
}
