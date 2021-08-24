package br.ufma.lsdi.api.security.jwt.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import br.ufma.lsdi.api.security.jwt.ServletUtil;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		String url = request.getRequestURL().toString();
		String message = String.format("Não autorizado. Verifique o token de acesso ou "
				+ "realize o login por meio do endereço %sapi/v1/login. Caso não possua "
				+ "cadastro, realize o mesmo em %sapi/v1/users.", 
				url, url);
		
		//Comando se token errado ou ausente
		String json = ServletUtil.toJson("error", message);
		ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
	}
}
