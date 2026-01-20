package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.tecnoDesk.TecnoDesk.DTO.ApiPlacaResponseDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.VeiculoDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.Veiculo;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.VeiculoRepository;
import exception.BadRequest;
import exception.NotFound;
import reactor.core.publisher.Mono;

@Service
public class VeiculoService {

	@Autowired
	VeiculoRepository veiculoRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	DecriptService decriptService;
	
	@Autowired
	Utils utils;
	
	@Autowired
	WebClient webClient;
	
	@Value("${api.placa.endpoint}")
	public String token;

	public Veiculo criarVeiculo(VeiculoDTO dto,String codEmpresa) {
		try {
			
			Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));
			
			if (dto.getPlaca() == null || dto.getPlaca().isEmpty()) {
				throw new IllegalArgumentException("Placa é obrigatória");
			}
			
			Veiculo novoVeiculo = mapper.map(dto, Veiculo.class);
			novoVeiculo.setEmpresa(empresa);
			novoVeiculo.setSequencial((long)utils.callNextId(empresa.getId(), 13));
			return veiculoRepository.save(novoVeiculo);
			
		} catch (Exception e) {
			throw new BadRequest("Não foi possível cadastrar o veículo: "+ e.getMessage());
		}
		
	}


	public Mono<ApiPlacaResponseDTO> pegarInformacoesCarroApi(String placa) {
		try {
			return this.webClient.get()
			        .uri("/" + placa + "/" + this.token)
			        .accept(MediaType.APPLICATION_JSON)
			        .header("Accept", "application/json")
			        .retrieve()
			        .bodyToMono(ApiPlacaResponseDTO.class);
		} catch (Exception e) {
			throw new BadRequest("Não foi possível recuperar a placa do carro de forma dinâmica"+ e.getMessage());
		}
	    
	}
	
	
	public List<Veiculo> listarVeiculos() {
		return veiculoRepository.findAll();
	}

	public Optional<Veiculo> buscarPorId(long id) {
		return veiculoRepository.findById(id);
	}

	public Veiculo atualizarVeiculo(long id, Veiculo veiculo) {
		if (!veiculoRepository.existsById(id)) {
			throw new NotFound("Veículo não encontrado");
		}
		veiculo.setId(id);
		return veiculoRepository.save(veiculo);
	}

	public void deletarVeiculo(long id) {
		if (!veiculoRepository.existsById(id)) {
			throw new NotFound("Veículo não encontrado");
		}
		veiculoRepository.deleteById(id);
	}

}
