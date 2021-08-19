package br.ufma.lsdi.api.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/*
	 * UserDetails é uma interface que representa o usuário logado
	 */
	@GetMapping("/info")
	public UserDTO userInfo(@AuthenticationPrincipal User user) {
		return UserDTO.create(user);
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getusers() {
		List<UserDTO> users = userService.getUsers();
		return ResponseEntity.ok(users);
	}
	
	
	@PostMapping
	public ResponseEntity<UserDTO> salvar(@RequestBody UserDTO user) {
		UserDTO userDTO = userService.salvar(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
	}
}
