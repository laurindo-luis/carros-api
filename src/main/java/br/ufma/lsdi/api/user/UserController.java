package br.ufma.lsdi.api.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	/*
	 * UserDetails é uma interface que representa o usuário logado
	 */
	@GetMapping
	public UserDetails userInfo(@AuthenticationPrincipal UserDetails user) {
		return user;
	}
}
