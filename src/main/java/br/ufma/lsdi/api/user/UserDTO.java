package br.ufma.lsdi.api.user;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	private String nome;
	private String login;
	private String token;
	
	public static UserDTO create(User user, String token) {
		UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
		userDTO.setToken(token);
		return userDTO;
	}
	
	public String toJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper(); 
		return objectMapper.writeValueAsString(this);
	}
}
