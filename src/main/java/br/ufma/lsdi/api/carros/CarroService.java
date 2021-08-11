package br.ufma.lsdi.api.carros;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
