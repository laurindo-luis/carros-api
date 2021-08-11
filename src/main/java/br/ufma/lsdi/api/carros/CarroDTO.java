package br.ufma.lsdi.api.carros;

import org.modelmapper.ModelMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarroDTO {
	
	private Long id;
	private String modelo;
	private String cor;
	
	public static CarroDTO create(Carro carro) {
		return new ModelMapper().map(carro, CarroDTO.class);
	}
}
