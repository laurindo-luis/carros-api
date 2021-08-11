package br.ufma.lsdi.api.carros;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.lsdi.api.exception.ObjectNotFoundException;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	public List<CarroDTO> getCarros() {
		return carroRepository.findAll()
				.stream()
				.map(CarroDTO::create)
				.collect(Collectors.toList());
	}
	
	public CarroDTO getCarroById(Long id) {
		return carroRepository
				.findById(id)
				.map(CarroDTO::create)
				.orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
	}
	
	public CarroDTO salvar(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possível inserir o registro!");
		
		return CarroDTO.create(carroRepository.save(carro));
	}
	
	public void deletar(Long id) {
		carroRepository.deleteById(id);
	}
}
