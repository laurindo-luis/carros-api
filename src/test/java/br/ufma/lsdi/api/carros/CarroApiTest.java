package br.ufma.lsdi.api.carros;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.ufma.lsdi.BaseApiTest;

public class CarroApiTest extends BaseApiTest {
	
	@Test
	public void getCarro() {
		ResponseEntity<CarroDTO> responseEntity = get("/api/v1/carros/1", CarroDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void salvar() {
		
		Carro carro = new Carro(null, "Gol G6", "Branco", "Carro novo", 
				"", "", "", "");
		ResponseEntity<Void> responseEntity = post("/api/v1/carros", carro, null);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
	@Test
	public void delete() {
		ResponseEntity<Void> responseEntity = delete("/api/v1/carros/2", null);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void put() {
		CarroDTO carroDTO = get("/api/v1/carros/1", CarroDTO.class).getBody();
		Carro carro = new ModelMapper().map(carroDTO, Carro.class);
		
		String nomeAntigo = carro.getNome();
		String nomeAtualizado = nomeAntigo.concat(" - Atualizado");
		
		//Atualizando nome
		carro.setNome(nomeAtualizado);
		ResponseEntity<CarroDTO> responseEntity = put("/api/v1/carros", carro, CarroDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		//Verificar se o nome do carro foi atualizado
		Long id = carro.getId();
		carroDTO = get("/api/v1/carros/"+id, CarroDTO.class).getBody();
		assertEquals(carroDTO.getNome(), nomeAtualizado);
	}
	
	@Test
	public void getCarros() {
		ResponseEntity<PageOutput> responseEntity = get("/api/v1/carros?page=0&size=30", PageOutput.class);
		List<CarroDTO> carros = (List<CarroDTO>)responseEntity.getBody().getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(30, carros.size());	
	}
}
