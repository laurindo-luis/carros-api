package br.ufma.lsdi.api.carros;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.ufma.lsdi.CONSTANTES;

@RestController
@RequestMapping(CONSTANTES.PATH_CARROS)
public class CarroController {
	
	@Autowired
	private CarroService carroService;
	
	@GetMapping
	public ResponseEntity<PageOutput> getCarros(@RequestParam(value = "page", defaultValue="0") Integer page,
			@RequestParam(value = "size", defaultValue="10") Integer size) {
		PageOutput pageOutput = carroService.getCarros(PageRequest.of(page, size));
		return ResponseEntity.ok(pageOutput);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CarroDTO> getCarroById(@PathVariable("id") Long id) {
		CarroDTO carro = carroService.getCarroById(id);
		return ResponseEntity.ok(carro);
	}
	
	@PostMapping
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<?> salvar(@RequestBody Carro carro) {
		CarroDTO carroDTO = carroService.salvar(carro);
		return ResponseEntity.created(getURI(carroDTO.getId())).build();
	}
	
	@PutMapping
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<?> atualizar(@RequestBody Carro carro) {
		return ResponseEntity.ok(carroService.atualizar(carro));
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
