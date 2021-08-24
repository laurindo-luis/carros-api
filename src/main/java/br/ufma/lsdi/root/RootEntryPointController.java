package br.ufma.lsdi.root;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.lsdi.api.carros.CarroController;
import br.ufma.lsdi.api.users.UserController;

@RestController
public class RootEntryPointController {
	
	@GetMapping
	public RootEntryPoint root() {
		RootEntryPoint rootEntryPoint = new RootEntryPoint();
		rootEntryPoint.add(
				linkTo(methodOn(UserController.class).salvar(null)
			).withRel("cadastrar-usuario").withType("POST"));	
		
		rootEntryPoint.add(
				linkTo(methodOn(CarroController.class).getCarros(0, 10)
			).withRel("lista-carros").withType("GET"));

		return rootEntryPoint;
	}
}
