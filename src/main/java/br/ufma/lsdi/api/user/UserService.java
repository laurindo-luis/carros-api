package br.ufma.lsdi.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}
	
	public void salvar(User user) {
		Assert.isNull(user.getId(), "Não foi possível salvar o registro. Id não é null");
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setSenha(encoder.encode(user.getSenha()));
		
		userRepository.save(user);
	}
}
