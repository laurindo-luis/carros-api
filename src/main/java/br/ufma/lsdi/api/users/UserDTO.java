package br.ufma.lsdi.api.users;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufma.lsdi.api.roles.RoleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	
    @ApiModelProperty(example = "null")
	private Long id;
	private String nome;
	private String login;
	private String senha;
	
	@ApiModelProperty(example = "null")
	private String token;
	private List<RoleDTO> roles;
	
	public static UserDTO create(User user) {
		UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
		
		userDTO.roles = user.getRoles().stream()
				.map(RoleDTO::create)
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
