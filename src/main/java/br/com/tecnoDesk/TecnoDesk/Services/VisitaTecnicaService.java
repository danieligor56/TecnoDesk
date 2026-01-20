package br.com.tecnoDesk.TecnoDesk.Services;

import br.com.tecnoDesk.TecnoDesk.DTO.VisitaTecnicaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.VisitaTecnica;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusVisita;
import br.com.tecnoDesk.TecnoDesk.Repository.ClienteRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ColaboradorRespository; // Note the typo in the original repo name
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.VisitaTecnicaRepository;
import exception.BadRequest;
import exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitaTecnicaService {

    @Autowired
    private VisitaTecnicaRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ColaboradorRespository colaboradorRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DecriptService decriptService;

    @Autowired
    private Utils utils;

    public List<VisitaTecnicaDTO> listarPorEmpresa(String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        return repository.findByEmpresaIdOrderByDataVisitaDescHoraVisitaDesc(codEmp).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public VisitaTecnicaDTO criar(VisitaTecnicaDTO dto, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        Empresa empresa = empresaRepository.findById(codEmp)
                .orElseThrow(() -> new NotFound("Empresa não encontrada"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFound("Cliente não encontrado"));

        Colaborador tecnico = colaboradorRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new NotFound("Técnico não encontrado"));

        if (!tecnico.isAtvReg()) {
            throw new BadRequest("O técnico selecionado não está ativo.");
        }

        VisitaTecnica visita = new VisitaTecnica();
        visita.setSequencial((long) utils.callNextId(codEmp, 14));
        visita.setEmpresa(empresa);
        visita.setCliente(cliente);
        visita.setTecnico(tecnico);
        visita.setDataVisita(dto.getDataVisita());
        visita.setHoraVisita(dto.getHoraVisita());
        visita.setTipoVisita(dto.getTipoVisita());
        visita.setStatusVisita(dto.getStatusVisita() != null ? dto.getStatusVisita() : StatusVisita.AGENDADA);
        visita.setObservacoes(dto.getObservacoes());
        visita.setRelatorioTecnico(dto.getRelatorioTecnico());
        visita.setEnderecoVisita(dto.getEnderecoVisita());
        visita.setProximaVisitaSugerida(dto.getProximaVisitaSugerida());

        visita = repository.save(visita);
        return toDTO(visita);
    }

    @Transactional
    public VisitaTecnicaDTO atualizar(Long id, VisitaTecnicaDTO dto, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        VisitaTecnica visita = repository.findById(id)
                .orElseThrow(() -> new NotFound("Visita técnica não encontrada"));

        if (visita.getEmpresa().getId() != codEmp) {
            throw new BadRequest("A visita técnica não pertence a esta empresa.");
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFound("Cliente não encontrado"));

        Colaborador tecnico = colaboradorRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new NotFound("Técnico não encontrado"));

        visita.setCliente(cliente);
        visita.setTecnico(tecnico);
        visita.setDataVisita(dto.getDataVisita());
        visita.setHoraVisita(dto.getHoraVisita());
        visita.setTipoVisita(dto.getTipoVisita());
        visita.setStatusVisita(dto.getStatusVisita());
        visita.setObservacoes(dto.getObservacoes());
        visita.setRelatorioTecnico(dto.getRelatorioTecnico());
        visita.setEnderecoVisita(dto.getEnderecoVisita());
        visita.setProximaVisitaSugerida(dto.getProximaVisitaSugerida());

        visita = repository.save(visita);
        return toDTO(visita);
    }

    @Transactional
    public void excluir(Long id, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        VisitaTecnica visita = repository.findById(id)
                .orElseThrow(() -> new NotFound("Visita técnica não encontrada"));

        if (visita.getEmpresa().getId() != codEmp) {
            throw new BadRequest("A visita técnica não pertence a esta empresa.");
        }

        repository.delete(visita);
    }

    @Transactional
    public VisitaTecnicaDTO alterarStatus(Long id, StatusVisita novoStatus, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        VisitaTecnica visita = repository.findById(id)
                .orElseThrow(() -> new NotFound("Visita técnica não encontrada"));

        if (visita.getEmpresa().getId() != codEmp) {
            throw new BadRequest("A visita técnica não pertence a esta empresa.");
        }

        visita.setStatusVisita(novoStatus);
        visita = repository.save(visita);
        return toDTO(visita);
    }

    private VisitaTecnicaDTO toDTO(VisitaTecnica visita) {
        return new VisitaTecnicaDTO(
                visita.getId(),
                visita.getSequencial(),
                visita.getCliente().getId(),
                visita.getCliente().getNome(),
                visita.getTecnico().getId(),
                visita.getTecnico().getNome(), // Colaborador has 'nome'
                visita.getDataVisita(),
                visita.getHoraVisita(),
                visita.getTipoVisita(),
                visita.getStatusVisita(),
                visita.getObservacoes(),
                visita.getRelatorioTecnico(),
                visita.getEnderecoVisita(),
                visita.getProximaVisitaSugerida(),
                visita.getEmpresa().getId());
    }
}
