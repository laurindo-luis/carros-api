package br.ufma.lsdi.api.roles;

import lombok.Data;

import org.modelmapper.ModelMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Data
public class RoleDTO {
	private Long id;
	private String nome;
	
	public static RoleDTO create(Role role) {
		return new ModelMapper().map(role, RoleDTO.class);
	}
}
