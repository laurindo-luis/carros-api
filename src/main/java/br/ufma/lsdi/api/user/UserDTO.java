package br.ufma.lsdi.api.user;

import org.modelmapper.ModelMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	private Long id;
	private String nome;
	private String login;
	private String senha;
	
	public UserDTO create(User user) {
		return new ModelMapper().map(user, UserDTO.class);
	}
}
