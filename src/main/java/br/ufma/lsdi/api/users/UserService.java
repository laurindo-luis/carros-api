package br.ufma.lsdi.api.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.ufma.lsdi.api.roles.Role;
import br.ufma.lsdi.api.roles.RoleDTO;
import br.ufma.lsdi.api.security.jwt.JwtUtil;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}
	
	public UserDTO salvar(UserDTO userDTO) {
		Assert.isNull(userDTO.getId(), "Não foi possível salvar o registro. Id deve ser null");
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userDTO.setSenha(encoder.encode(userDTO.getSenha()));
		
		User user = User.create(userDTO);
		UserDTO userResponse = UserDTO.create(userRepository.save(user));
		userResponse.setSenha(null);
		return userResponse;
	}
	
	public List<UserDTO> getUsers() {
		return userRepository.findAll()
				.stream()
				.map(UserDTO::create)
				.collect(Collectors.toList());
	}
}
