package br.ufma.lsdi.api.carros;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {
	
	@Autowired
	private CarroService carroService;
	
	@GetMapping
	public ResponseEntity<?> getCarro() {
		List<CarroDTO> carros = carroService.getCarros();
		return ResponseEntity.ok(carros);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCarroById(@PathVariable("id") Long id) {
		CarroDTO carro = carroService.getCarroById(id);
		return ResponseEntity.ok(carro);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Carro carro) {
		CarroDTO carroDTO = carroService.salvar(carro);
		return ResponseEntity.created(getURI(carroDTO.getId())).build();
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable("id") Long id) {
		carroService.deletar(id);
	}
	
	private URI getURI(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
				.buildAndExpand(id).toUri();
	}
}
