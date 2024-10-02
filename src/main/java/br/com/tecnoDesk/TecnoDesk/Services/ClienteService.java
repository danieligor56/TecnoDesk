package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.ClienteDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.ColaboradorDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Repository.ClienteRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import exception.BadRequest;
import exception.NotFound;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	EncryptionUtil secUtil;
	
	@Autowired
	DecriptService decriptService;

	public Cliente criaNovoCliente(ClienteDTO clienteDTO, String codEmpresa) {
		try {
			
			Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));
			clienteDTO.setEmpresa(empresa);
			Cliente novoCliente = modelMapper.map(clienteDTO, Cliente.class);
			return clienteRepository.save(novoCliente);

		} catch (Exception e) {
			throw new BadRequest("Não foi possível atender a solicitação no momento");
		}

		
		
	}

	public List<Cliente> listarClientes(long Codemp) {
		return clienteRepository.listAll(Codemp);
	}

	public Cliente buscarCliporId(long id,String codEmpresa) {

		try {
			
			Cliente cli = clienteRepository.findItById(id);
			
						
			if(cli.getEmpresa().getId() == decriptService.decriptCodEmp(codEmpresa)) {
			return cli;
			
			}
			
			else {
				throw new NotFound("Não há colaboradores cadastrados com esse ID");
			}
		
			} 
	
			catch (Exception e) {
			throw new BadRequest("Não foi possível atender sua solicitação nesse moemnto");
			}

			}

	public List<Cliente> buscarPorNome(String nome) {
		return clienteRepository.buscarPornome(nome);
	}

	public Cliente alterarCliente(long id, ClienteDTO clienteDTO,String codEmpresa) {
			
			try {
				
				Cliente cli = clienteRepository.findItById(id);
				
				if (clienteRepository.existsById(cli.getId())) {
				
					if(cli.getEmpresa().getId() == Long.valueOf(decriptService.decriptCodEmp(codEmpresa))) {
					
						Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
						cliente.setId(id);
						clienteRepository.save(cliente);
					}
				
				}
			
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return null;
		}

	
	public Cliente buscaPorDoc(String doc,String codEmpresa) throws Exception {
		
		String chave = secUtil.decrypt(codEmpresa);

		if (!clienteRepository.existsByDocumento(doc)) {
			throw new NotFound("Nenhum cliente encontrado com o documento fornecido");
		}
		
		Cliente cli = clienteRepository.findByDocumento(doc);
		
		if(cli.getEmpresa().getId() != Long.valueOf(chave)) {
			throw new NotFound("Nenhum cliente encontrado com o documento fornecido");
		}
		
		return cli;
	}
	
	public void deletarCliente(long id, String codEmpresa) throws Exception {
	
			Cliente existeCliente = clienteRepository.findItById(id);
			
			if (existeCliente != null) {
				
				 String chave = secUtil.decrypt(codEmpresa);
				
				if(existeCliente.getEmpresa().getId() == Long.valueOf(chave))
				
					clienteRepository.deleteById(id);
				
			}
			
			else {
				throw new NotFound("Não há clientes cadastrados com esse ID");
			}
			
	
			}

}
