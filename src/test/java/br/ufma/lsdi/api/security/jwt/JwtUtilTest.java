package br.ufma.lsdi.api.security.jwt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.ufma.lsdi.api.user.User;
import br.ufma.lsdi.api.user.UserDTO;
import br.ufma.lsdi.api.user.UserService;

@SpringBootTest
public class JwtUtilTest {
	
	@Autowired
	UserService userService;
	
	@Test
	public void testToken() {
		
		//Recuperando o usuário
		UserDetails user = userService.getUserByLogin("luis");
		assertNotNull(user);
		assertEquals(user.getUsername(), "luis");
		
		//Criando Token
		String token = JwtUtil.createToken(user);
		assertNotNull(token);
		
		//Verificando validade do Token
		boolean ok = JwtUtil.isTokenValid(token);
		assertTrue(ok);
		
		//validar login
		String login = JwtUtil.getLogin(token);
		assertEquals(login, "luis");
		
		//Verifica roles
		List<GrantedAuthority> roles = JwtUtil.getRoles(token);
		assertEquals(roles.get(0).getAuthority(), "ROLE_USER");		
	}
}
