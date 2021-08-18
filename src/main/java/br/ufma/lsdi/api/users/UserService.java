package br.ufma.lsdi.api.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.ufma.lsdi.api.security.jwt.JwtUtil;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}
	
	public UserDTO salvar(User user) {
		Assert.isNull(user.getId(), "Não foi possível salvar o registro. Id não é null");
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setSenha(encoder.encode(user.getSenha()));
		
		userRepository.save(user);
		
		String token = JwtUtil.createToken(user);
		return UserDTO.create(user, token);
	}
	
	public List<UserDTO> getUsers() {
		return userRepository.findAll()
				.stream()
				.map(UserDTO::create)
				.collect(Collectors.toList());
	}
}
