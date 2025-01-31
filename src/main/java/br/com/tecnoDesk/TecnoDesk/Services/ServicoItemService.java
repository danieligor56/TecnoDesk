package br.com.tecnoDesk.TecnoDesk.Services;

import br.com.tecnoDesk.TecnoDesk.DTO.ServicoItemDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.ServicoItem;
import br.com.tecnoDesk.TecnoDesk.Repository.ServicoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicoItemService {

	private final ServicoItemRepository servicoItemRepository;

	public ServicoItem criarServicoItem(ServicoItemDTO servicoItemDTO) {
		ServicoItem servicoItem = new ServicoItem();
		servicoItem.setNome_servico(servicoItemDTO.nomeServico());
		servicoItem.setDesc_servico(servicoItemDTO.descServico());
		servicoItem.setValor_servico(servicoItemDTO.valorServico());
		servicoItem.setCusto_servico(servicoItemDTO.custoServico());
		return servicoItemRepository.save(servicoItem);
	}

	public ServicoItemDTO atualizarServicoItem(Long id, ServicoItemDTO servicoItemDTO) {
		return servicoItemRepository.findById(id)
				.map(servicoItem -> {
					servicoItem.setNome_servico(servicoItemDTO.nomeServico());
					servicoItem.setDesc_servico(servicoItemDTO.descServico());
					servicoItem.setValor_servico(servicoItemDTO.valorServico());
					servicoItem.setCusto_servico(servicoItemDTO.custoServico());
					ServicoItem atualizado = servicoItemRepository.save(servicoItem);
					return new ServicoItemDTO(
							atualizado.getNome_servico(),
							atualizado.getDesc_servico(),
							atualizado.getValor_servico(),
							atualizado.getCusto_servico());
				})
				.orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
	}

	public void deletarServicoItem(Long id) {
		servicoItemRepository.deleteById(id);
	}
}
