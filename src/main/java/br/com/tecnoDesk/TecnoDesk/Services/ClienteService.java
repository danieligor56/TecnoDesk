package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.DTO.ClienteDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Repository.ClienteRepository;
import exception.NotFound;
import lombok.experimental.var;

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

	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}

	public Cliente buscarCliporId(long id) {

		Optional<Cliente> client = clienteRepository.findById(id);

		if (client.isPresent()) {

			return client.get();
		}

		throw new NotFound("Não há clientes cadastrados com esse ID");
	}

	public List<Cliente> buscarPorNome(String nome) {
		return clienteRepository.buscarPornome(nome);
	}

	public Cliente alterarCliente(long id, ClienteDTO clienteDTO) {
		Cliente clienteAlt = modelMapper.map(clienteDTO, Cliente.class);
		clienteAlt.setId(id);
		return clienteRepository.save(clienteAlt);

	}

	public Cliente buscaPorDoc(long doc) {

		if (!clienteRepository.existsByDocumento(doc)) {
			throw new NotFound("Nenhum cliente encontrado com o documento fornecido");
		}
		Cliente cli = clienteRepository.findByDocumento(doc);
		return cli;
	}

}
