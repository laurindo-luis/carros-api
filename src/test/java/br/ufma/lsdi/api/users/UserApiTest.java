package br.ufma.lsdi.api.users;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.ufma.lsdi.BaseApiTest;
import br.ufma.lsdi.api.roles.RoleDTO;


public class UserApiTest extends BaseApiTest {
	
	@Test
	public void getUser() {
		ResponseEntity<UserDTO> responseEntity = get("/api/v1/users/info", UserDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}
	
	@Test
	public void salvar() throws JsonProcessingException {
		RoleDTO role = new RoleDTO(1l, "ROLE_USER");
		UserDTO user = new UserDTO(null, "Luis Laurindo", "luis_laurindo_costa", "123", 
				null, Arrays.asList(role));
		
		System.out.println("User -> "+user);
		
		ResponseEntity<UserDTO> responseEntity = post("/api/v1/users", user, UserDTO.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("luis_laurindo_costa", responseEntity.getBody().getLogin());
	}
}
