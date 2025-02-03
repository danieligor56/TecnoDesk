package br.com.tecnoDesk.TecnoDesk.Services;

import br.com.tecnoDesk.TecnoDesk.DTO.ServicoItemDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.ServicoItem;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ServicoItemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoItemService {

	@Autowired
	DecriptService decriptService;

	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	ServicoItemRepository servicoItemRepository;


	//CRIAR SERVICO ITEM
	public ServicoItem criarServicoItem(ServicoItemDTO servicoItemDTO, String codEmpresa) throws BadRequestException {
		try {
			Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));
			if (empresa == null) {
				throw new BadRequestException("Empresa não encontrada para o código fornecido.");
			}
			ServicoItem servicoItem = new ServicoItem();
			servicoItem.setNomeServico(servicoItemDTO.nomeServico());
			servicoItem.setDescServico(servicoItemDTO.descServico());
			servicoItem.setValorServico(servicoItemDTO.valorServico());
			servicoItem.setCustoServico(servicoItemDTO.custoServico());
			servicoItem.setEmpresa(empresa);
			return servicoItemRepository.save(servicoItem);

		} catch (Exception e) {
			throw new BadRequestException("Não foi possível criar um Servico", e);
		}
	}

	//BUSCAR TODOS OS SERVICOS DO GRUPO DE EMPRESAS
	public List<ServicoItem> buscarTodos(String codEmpresa) throws BadRequestException {
		try {
			Long empresaId = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			return servicoItemRepository.listSevicoItem(empresaId);

		} catch (Exception e) {
			throw new BadRequestException("Não foi possível buscar os serviços", e);
		}
	}

	//ATUALIZAR SERVICO ITEM
	public ServicoItemDTO atualizarServicoItem(Long id, ServicoItemDTO servicoItemDTO) {
		return servicoItemRepository.findById(id)
				.map(servicoItem -> {
					servicoItem.setNomeServico(servicoItemDTO.nomeServico());
					servicoItem.setDescServico(servicoItemDTO.descServico());
					servicoItem.setValorServico(servicoItemDTO.valorServico());
					servicoItem.setCustoServico(servicoItemDTO.custoServico());
					ServicoItem atualizado = servicoItemRepository.save(servicoItem);
					return new ServicoItemDTO(
							atualizado.getNomeServico(),
							atualizado.getDescServico(),
							atualizado.getValorServico(),
							atualizado.getCustoServico());
				})
				.orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
	}

	//DELETAR SERVICO ITEM
	public ResponseEntity<String> deletarServicoItem(Long id) {
		if (servicoItemRepository.existsById(id)) {
			servicoItemRepository.deleteById(id);
			return ResponseEntity.ok("Serviço com ID " + id + " foi deletado com sucesso.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço com ID " + id + " não encontrado.");
		}
	}

}
