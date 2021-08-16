package br.ufma.lsdi.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufma.lsdi.api.users.User;
import br.ufma.lsdi.api.users.UserService;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		
//		if(username.equals("user"))
//			return User.withUsername(username).password(encoder.encode("user")).roles("USER").build();
//		else if(username.equals("admin"))
//			return User.withUsername(username).password(encoder.encode("admin")).roles("USER", "ADMIN").build();
//		
//		br.ufma.lsdi.api.user.User user = userService.getUserByLogin(username);
//		if(user != null) {
//			return User.withUsername(username).password(user.getSenha()).roles("USER").build();
//		}
		
		User user = userService.getUserByLogin(username);
		if(user == null)
			throw new UsernameNotFoundException("Usuário não econtrado");

		return user;		
	}
}
