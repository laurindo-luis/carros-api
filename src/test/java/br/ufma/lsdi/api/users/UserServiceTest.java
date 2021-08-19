package br.ufma.lsdi.api.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.lsdi.api.roles.Role;
import br.ufma.lsdi.api.roles.RoleDTO;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void salvar()  {
		RoleDTO role = new RoleDTO(1l, "ROLE_USER");
		UserDTO user = new UserDTO(null, "Luis Laurindo", "luis_laurindo", "123", 
				null, Arrays.asList(role));
		
		UserDTO userDTO = userService.salvar(user);
		assertNotNull(userDTO.getId());
		assertEquals("ROLE_USER", userDTO.getRoles().get(0).getNome());
	}
	
	@Test
	public void getUserByLogin() {
		User user = userService.getUserByLogin("luis");
		assertNotNull(user);	
	}
	
	@Test
	public void getUsers() {
		List<UserDTO> users = userService.getUsers();
		assertEquals(3, users.size());
	}
}
