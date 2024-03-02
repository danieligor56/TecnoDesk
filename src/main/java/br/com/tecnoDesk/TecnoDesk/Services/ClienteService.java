package br.com.tecnoDesk.TecnoDesk.Services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.DTO.ClienteDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	public Cliente criaNovoCliente(ClienteDTO clienteDTO) {
		Cliente novoCliente = modelMapper.map(clienteDTO, Cliente.class);
		return clienteRepository.save(novoCliente);
	}
	
}
