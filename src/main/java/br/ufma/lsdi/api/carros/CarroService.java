package br.ufma.lsdi.api.carros;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufma.lsdi.api.exception.ObjectNotFoundException;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	public PageOutput getCarros(Pageable pageable) {
		
		Page<Carro> page = carroRepository.findAll(pageable);
		List<CarroDTO> carros = page.stream()
				.map(CarroDTO::create)
				.collect(Collectors.toList());
		
		return new PageOutput(page.getTotalPages(), pageable.getPageNumber() + 1, 
				pageable.getPageSize(), carros);
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
	
	public CarroDTO atualizar(Carro carro) {
		Assert.notNull(carro.getId(), "Não é possível atualizar o registro!");
		getCarroById(carro.getId());
		return CarroDTO.create(carroRepository.save(carro));
	}
	
	public void deletar(Long id) {
		carroRepository.deleteById(id);
	}
}
