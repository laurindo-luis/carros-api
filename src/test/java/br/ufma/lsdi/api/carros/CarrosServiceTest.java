package br.ufma.lsdi.api.carros;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.ufma.lsdi.api.exception.ObjectNotFoundException;
import io.jsonwebtoken.lang.Assert;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CarrosServiceTest {
	
	@Autowired
	CarroService carroService;
	
	@Test
	@Order(1)
	public void createGetDeleteCarro() {
		
		//Inserir carro
		Carro c = new Carro(null, "Gol G6", "Branco", "Carro novo", 
				null, null, null, null);
		CarroDTO carroDTO = carroService.salvar(c);
		Long id = carroDTO.getId();
		Assert.notNull(id);
		
		//Buscar carro inserido
		carroDTO = null;
		carroDTO = carroService.getCarroById(id);
		Assert.notNull(carroDTO);
		
		//Deletar carro inserido
		carroService.deletar(id);
		
		//Buscando o carro para ver se foi deletado
		assertThrows(ObjectNotFoundException.class, () -> carroService.getCarroById(id));
	}
	
	@Test
	@Order(2)
	public void getCarros() {
		List<CarroDTO> carros = (List<CarroDTO>) carroService.getCarros(PageRequest.of(0, 100)).getBody();
		assertEquals(30, carros.size());
	}
}
