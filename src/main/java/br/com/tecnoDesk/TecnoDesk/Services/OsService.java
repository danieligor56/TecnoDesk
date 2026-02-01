package br.com.tecnoDesk.TecnoDesk.Services;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import exception.BadRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.tecnoDesk.TecnoDesk.DTO.OS_EntradaDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.TecnicoEPrioridadeDTO;
import br.com.tecnoDesk.TecnoDesk.DTO.UpdateLaudoTecnicoDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.Orcamento;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import br.com.tecnoDesk.TecnoDesk.Enuns.PrioridadeOS;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOR;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import br.com.tecnoDesk.TecnoDesk.Repository.ClienteRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ColaboradorRespository;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoItemRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OrcamentoRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;

@Service
public class OsService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	OsRepository osRepository;

	@Autowired
	ColaboradorRespository colaboradorRespository;

	@Autowired
	DecriptService decriptService;

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	OrcamentoRepository orcamentoRepository;

	@Autowired
	OrcamentoItemRepository orcamentoItemRepository;

	@Autowired
	Utils utils;

	@Autowired
	public HistoricoOSService historicoOSService;

	public OS_Entrada crianova(OS_EntradaDTO osDTO, String codEmpresa) {

		try {

			Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));

			OS_Entrada novaOs = modelMapper.map(osDTO, OS_Entrada.class);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			formatter.format(now);
			novaOs.setDataAbertura(formatter.format(now));
			novaOs.setEmpresa(empresa);

			/* OS_Entrada codUltimaOs = osRepository.findLastOne(empresa.getId()); */
			/*
			 * if(codUltimaOs == null) { novaOs.setNumOs(1); } else {
			 * novaOs.setNumOs(codUltimaOs.getNumOs() + 1); }
			 */
			novaOs.setSequencial((long) utils.callNextId(empresa.getId(), 7));

			osRepository.save(novaOs);

			Orcamento novoOrcamentoOS = new Orcamento();
			novoOrcamentoOS.setEmpresa(empresa);
			novoOrcamentoOS.setOs(novaOs);
			novoOrcamentoOS.setStatusOR(StatusOR.NOVO);
			orcamentoRepository.save(novoOrcamentoOS);

			historicoOSService.registrar(novaOs, "OS Aberta");

			return novaOs;

		} catch (Exception e) {
			throw new BadRequest("Não foi possível atender a solicitação no momento", e);
		}

	}

	public List<OS_Entrada> listarOS(String codEmpresa) throws Exception {
		List<OS_Entrada> oss = this.osRepository
				.findOsByEmpresaId(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));
		return oss;
	}

	public List<OS_Entrada> listarOsCanceladasEncerrada(String codEmpresa) throws Exception {
		List<OS_Entrada> oss = this.osRepository
				.listarOsCanceladasEncerrada(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));
		return oss;
	}

	public OS_Entrada buscarPorNumOs(long numOs, String codEmpresa) throws Exception {
		Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));

		if (empresa != null) {
			OS_Entrada os = osRepository.findByNumOs(numOs, empresa.getId());
			return os;
		}

		throw new BadRequest("Não foi possível atender sua solicitação nesse momento");
	}

	public void alterarTecnicoPrioridadeOs(TecnicoEPrioridadeDTO dto, String codEmpresa) {
		try {

			OS_Entrada os = osRepository.findByNumOs(dto.numOs, Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));

			if (dto.tecnicoId > 0) {
				os.setTecnico_responsavel(colaboradorRespository.findItById(dto.tecnicoId,
						Long.valueOf(decriptService.decriptCodEmp(codEmpresa))));
			}

			switch (dto.prioridadeOS) {

				case 0: {

					os.setPrioridadeOS(PrioridadeOS.NORMAL);
					break;
				}

				case 1: {
					os.setPrioridadeOS(PrioridadeOS.URGENCIA);
					break;
				}

				case 2: {
					os.setPrioridadeOS(PrioridadeOS.GARANTIA);
					break;
				}

				case 3: {
					os.setPrioridadeOS(PrioridadeOS.PRIORITARIA);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + dto.prioridadeOS);
			}

			osRepository.save(os);

			historicoOSService.registrar(os, "Alterou técnico/prioridade da OS");

		} catch (Exception e) {
			throw new BadRequest("Não foi possível alterar a OS: " + e.getMessage());
		}

	}

	public void alterarStatusDaOS(long numOS, int stsOs, String codEmpresa) {
		if (numOS > 0 && codEmpresa != null && stsOs <= 9) {
			try {
				Empresa empresa = empresaRepository
						.findEmpresaById(Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));
				OS_Entrada os = osRepository.findByNumOs(numOS, empresa.getId());

				Orcamento orcamento = orcamentoRepository.encontrarOcamentoPorNumOS(empresa.getId(), os.getId());

				switch (stsOs) {
					case 0: {
						
						if(stsOs == 0 && os.getStatusOS() == StatusOS.NOVO) {
							orcamento.setStatusOR(StatusOR.NOVO);
							orcamentoRepository.save(orcamento);
						}
						os.setStatusOS(StatusOS.NOVO);
						break;
					}
					case 1: {

						if (os.getStatusOS() == StatusOS.ENCERRADA || os.getStatusOS() == StatusOS.CANCELADA)
							throw new BadRequest(
									"O.s cancelada ou encerrada, para reabrir a OS, marque a opção Encerradas e reabra a OS. ");

						os.setStatusOS(StatusOS.EM_ANDAMENTO);
						break;
					}
					case 2: {

						if (os.getStatusOS() == StatusOS.ENCERRADA || os.getStatusOS() == StatusOS.CANCELADA)
							throw new BadRequest(
									"O.s cancelada ou encerrada, para reabrir a OS, marque a opção Encerradas e reabra a OS. ");
						// AGUARDANDO RESPOSTA DO CLIENTE:

						if (orcamentoItemRepository.contarItensOrcamento(empresa.getId(), orcamento.getId()) == 0) {
							throw new BadRequest(
									"Não é possivel, oferecer o orçamento, pois não há itens ou serviços definidos.");
						}

						os.setStatusOS(StatusOS.AGUARDANDO_RESP_ORCAMENTO);
						orcamento.setStatusOR(StatusOR.AGUARDANDO_RESPOSTA);
						orcamentoRepository.save(orcamento);

						break;
					}
					case 3: {

						if (os.getStatusOS() == StatusOS.ENCERRADA || os.getStatusOS() == StatusOS.CANCELADA)
							throw new BadRequest(
									"O.s cancelada ou encerrada, para reabrir a OS, marque a opção Encerradas e reabra a OS. ");

						os.setStatusOS(StatusOS.AGUARDANDO_PECAS);
						break;
					}
					case 4: {

						if (os.getStatusOS() == StatusOS.ENCERRADA || os.getStatusOS() == StatusOS.CANCELADA)
							throw new BadRequest(
									"O.s cancelada ou encerrada, para reabrir a OS, marque a opção Encerradas e reabra a OS. ");

						os.setStatusOS(StatusOS.AGUARDANDO_RETIRADA);
						break;
					}
					case 5: {

						if (os.getStatusOS() == StatusOS.ENCERRADA || os.getStatusOS() == StatusOS.CANCELADA)
							throw new BadRequest(
									"O.s cancelada ou encerrada, para reabrir a OS, marque a opção Encerradas e reabra a OS. ");

						if (orcamentoItemRepository.contarItensOrcamento(empresa.getId(), orcamento.getId()) == 0) {
							throw new BadRequest(
									"Não é possivel aprovar o orçamento pois não há produtos ou serviços definidos. ");
						}

						os.setStatusOS(StatusOS.ORCAMENTO_APROVADO);
						orcamento.setStatusOR(StatusOR.APROVADO);
						orcamentoRepository.save(orcamento);
						break;
					}
					case 6: {

						if (os.getStatusOS() == StatusOS.ENCERRADA || os.getStatusOS() == StatusOS.CANCELADA)
							throw new BadRequest(
									"O.s cancelada ou encerrada, para reabrir a OS, marque a opção Encerradas e reabra a OS. ");

						os.setStatusOS(StatusOS.PENDENTE);
						break;
					}
					case 7: {

						if (os.getStatusOS() == StatusOS.ENCERRADA || os.getStatusOS() == StatusOS.CANCELADA)
							throw new BadRequest(
									"O.s cancelada ou encerrada, para reabrir a OS, marque a opção Encerradas e reabra a OS. ");

						os.setStatusOS(StatusOS.CONCLUIDO);
						break;
					}
					case 8: {
						os.setStatusOS(StatusOS.CANCELADA);
						orcamento.setStatusOR(StatusOR.ENCERRADO);
						orcamentoRepository.save(orcamento);
						break;
					}
					case 9: {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
						LocalDateTime now = LocalDateTime.now();
						os.setDataEncerramento(formatter.format(now));
						os.setStatusOS(StatusOS.ENCERRADA);

						orcamento.setStatusOR(StatusOR.ENCERRADO);
						orcamentoRepository.save(orcamento);

						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + stsOs);
				}

				osRepository.save(os);

				historicoOSService.registrar(os, "Mudou status da OS para " + os.getStatusOS().name());

			} catch (Exception e) {
				throw new BadRequest(e.getMessage());
			}

		} else {
			throw new BadRequest("Você não forneceu parametros suficientes para a requisição");
		}
	}

	public void salvarDiagnosticoTecnico(UpdateLaudoTecnicoDTO dto, String codEmpresa) throws Exception {

		try {
			var os = this.osRepository.findByNumOs(dto.numOS, Long.valueOf(decriptService.decriptCodEmp(codEmpresa)));

			if (os == null) {
				throw new BadRequest("OS não encontrada.");
			}

			if (os.getStatusOS() == StatusOS.CANCELADA || os.getStatusOS() == StatusOS.CONCLUIDO
					|| os.getStatusOS() == StatusOS.ENCERRADA) {
				throw new BadRequest("Status da OS não permite que o campo seja preenchido.");
			}

			os.setLaudoTecnico(dto.laudo);
			osRepository.save(os);

			historicoOSService.registrar(os, "Atualizou laudo técnico");

		} catch (Exception e) {
			throw new BadRequest("Não foi possível atender a requisição nesse momento: " + e.getMessage());
		}
	}

}
