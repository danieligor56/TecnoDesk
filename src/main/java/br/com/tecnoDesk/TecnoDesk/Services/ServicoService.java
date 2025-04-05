package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;
import br.com.tecnoDesk.TecnoDesk.DTO.ClienteDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.ServicoDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.Servico;
import br.com.tecnoDesk.TecnoDesk.Repository.ClienteRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ServicoRepository;
import exception.BadRequest;
import exception.NotFound;

@Service
public class ServicoService {

	/*
	 * @Autowired ClienteRepository clienteRepository;
	 */

	@Autowired
	ServicoRepository servicoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	EncryptionUtil secUtil;
	
	@Autowired
	DecriptService decriptService;
	
	public Servico criarNovoServico(ServicoDTO servicoDTO, String codEmpresa) {
		try {
			
			Empresa empresa = empresaRepository.findEmpresaById(decriptService.decriptCodEmp(codEmpresa));
			servicoDTO.setEmpresa(empresa);
			Servico novoServico= modelMapper.map(servicoDTO, Servico.class);
			return servicoRepository.save(novoServico);

		} catch (Exception e) {
			throw new BadRequest("Não foi possível atender a solicitação no momento");
		}

	}
	
	public List<Servico> listarServico(Long Codemp) {
		return servicoRepository.listAll(Codemp);
	}

	public Servico buscarServicoPorId(long id,String codEmpresa) {

		try {
				long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
				Servico servico = servicoRepository.encontrarPorId(id,codEmp);
				return servico;
			} catch (Exception e) {
				throw new BadRequest("Não foi possível atender sua solicitação nesse moemnto");
					}
	}
	
	
	public Cliente alterarServico(long id, ServicoDTO servicoDTO,String codEmpresa) {
		
		try {
			long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
			
			Servico servico = servicoRepository.encontrarPorId(id,codEmp);
					servico.setNomeServico(servicoDTO.getNomeServico());
					servico.setDescricaoServico(servicoDTO.getDescricaoServico());
					servico.setValorServicoHora(servicoDTO.getValorServicoHora());
					servico.setValorServicoUnidade(servicoDTO.getValorServicoUnidade());
					servicoRepository.save(servico);
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
		
			
	




}
