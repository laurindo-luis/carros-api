package br.ufma.lsdi.api.carros;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarroDTO {
	
	private Long id;
	private String nome;
	private String tipo;
	private String descricao;
	private String urlFoto;
	private String urlVideo;
	private String latitude;
	private String longitude;
	
	public static CarroDTO create(Carro carro) {
		return new ModelMapper().map(carro, CarroDTO.class);
	}
}
