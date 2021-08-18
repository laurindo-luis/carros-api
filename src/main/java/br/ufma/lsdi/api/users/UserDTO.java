package br.ufma.lsdi.api.users;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	
	private Long id;
	private String nome;
	private String login;
	private String token;
	private List<String> roles;
	
	public static UserDTO create(User user) {
		UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
		
		userDTO.roles = user.getRoles().stream()
				.map(role -> role.getNome())
				.collect(Collectors.toList());
		
		return userDTO;
	}
		
	public static UserDTO create(User user, String token) {
		UserDTO userDTO = create(user);
		userDTO.setToken(token);
		return userDTO;
	}
	
	public String toJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper(); 
		return objectMapper.writeValueAsString(this);
	}
}
