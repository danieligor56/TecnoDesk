package br.com.tecnoDesk.TecnoDesk.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.DTO.OsRapidaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.OsRapida;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRapidaRepository;
import exception.BadRequest;

@Service
public class OsRapidaService {

    @Autowired
    OsRapidaRepository osRapidaRepository;

    @Autowired
    DecriptService decriptService;
    
    @Autowired
    Utils utils;

    public OsRapida criarOsRapida(OsRapidaDTO dto, String tecnicoResponsavel, String codEmpresa) throws BadRequest {
        try {
        	
        	long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
        	
            OsRapida osRapida = new OsRapida();
            osRapida.setClienteNome(dto.getClienteNome());
            osRapida.setClienteTelefone(dto.getClienteTelefone());
            osRapida.setEquipamentoServico(dto.getEquipamentoServico());
            osRapida.setProblemaRelatado(dto.getProblemaRelatado());
            osRapida.setValorEstimado(dto.getValorEstimado());
            osRapida.setPrazoCombinado(dto.getPrazoCombinado());
            osRapida.setObservacoes(dto.getObservacoes());

            // Set defaults
            osRapida.setStatus(StatusOS.NOVO);
            osRapida.setTecnicoResponsavel(tecnicoResponsavel);
            osRapida.setCodigoEmpresa(codEmp);
            osRapida.setId((long) utils.callNextId(codEmp,12));
            // Set current date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            osRapida.setDataAbertura(formatter.format(now));

            return osRapidaRepository.save(osRapida);

        } catch (Exception e) {
            throw new BadRequest("Não foi possível criar a OS rápida: " + e.getMessage());
        }
    }

    public List<OsRapida> listarOsRapidas(String codEmpresa) throws Exception {
        try {
            long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
            return osRapidaRepository.findByCodigoEmpresa(codEmp);
        } catch (Exception e) {
            throw new BadRequest("Não foi possível listar as OS rápidas");
        }
    }

    public List<OsRapida> listarOsRapidasPorTecnico(String tecnico, String codEmpresa) throws Exception {
        try {
            long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
            return osRapidaRepository.findByTecnicoResponsavel(tecnico, codEmp);
        } catch (Exception e) {
            throw new BadRequest("Não foi possível listar as OS rápidas do técnico");
        }
    }

    public OsRapida buscarOsRapidaPorId(Long id, String codEmpresa) throws Exception {
        try {
            OsRapida osRapida = osRapidaRepository.findById(id).orElseThrow(() ->
                new BadRequest("OS Rápida não encontrada com ID: " + id));

            // Verificar se a OS pertence à empresa do usuário
            long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
            if (!osRapida.getCodigoEmpresa().equals(codEmp)) {
                throw new BadRequest("Acesso negado: OS não pertence à sua empresa");
            }

            return osRapida;
        } catch (Exception e) {
            throw new BadRequest("Não foi possível buscar a OS rápida");
        }
    }

    public OsRapida atualizarOsRapida(Long id, OsRapidaDTO dto, String tecnicoResponsavel, String codEmpresa) throws BadRequest {
        try {
            OsRapida osRapidaExistente = osRapidaRepository.findById(id).orElseThrow(() ->
                new BadRequest("OS Rápida não encontrada com ID: " + id));

            // Verificar se a OS pertence à empresa do usuário
            long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
            if (!osRapidaExistente.getCodigoEmpresa().equals(codEmp)) {
                throw new BadRequest("Acesso negado: OS não pertence à sua empresa");
            }

            // Atualizar apenas os campos que podem ser editados
            osRapidaExistente.setClienteNome(dto.getClienteNome());
            osRapidaExistente.setClienteTelefone(dto.getClienteTelefone());
            osRapidaExistente.setEquipamentoServico(dto.getEquipamentoServico());
            osRapidaExistente.setProblemaRelatado(dto.getProblemaRelatado());
            osRapidaExistente.setValorEstimado(dto.getValorEstimado());
            osRapidaExistente.setPrazoCombinado(dto.getPrazoCombinado());
            osRapidaExistente.setObservacoes(dto.getObservacoes());

            // Não alterar o técnico responsável nem a data de abertura

            return osRapidaRepository.save(osRapidaExistente);

        } catch (Exception e) {
            throw new BadRequest("Não foi possível atualizar a OS rápida: " + e.getMessage());
        }
    }

    public void alterarStatusOsRapida(Long id, StatusOS novoStatus, String codEmpresa) throws BadRequest {
        try {
            OsRapida osRapida = osRapidaRepository.findById(id).orElseThrow(() ->
                new BadRequest("OS Rápida não encontrada com ID: " + id));

            // Verificar se a OS pertence à empresa do usuário
            long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresa));
            if (!osRapida.getCodigoEmpresa().equals(codEmp)) {
                throw new BadRequest("Acesso negado: OS não pertence à sua empresa");
            }

            // Verificar se o status permite alteração
            if (osRapida.getStatus() == StatusOS.CANCELADA || osRapida.getStatus() == StatusOS.ENCERRADA) {
                throw new BadRequest("Não é possível alterar o status de uma OS " + osRapida.getStatus().toString().toLowerCase());
            }

            osRapida.setStatus(novoStatus);

            // Definir datas conforme o status
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            if (novoStatus == StatusOS.ENCERRADA) {
                osRapida.setDataConclusao(formatter.format(now));
                osRapida.setDataCancelamento(null); // Limpar data de cancelamento se estava definida
            } else if (novoStatus == StatusOS.CANCELADA) {
                osRapida.setDataCancelamento(formatter.format(now));
                osRapida.setDataConclusao(null); // Limpar data de conclusão se estava definida
            }

            osRapidaRepository.save(osRapida);

        } catch (Exception e) {
            throw new BadRequest("Não foi possível alterar o status da OS rápida: " + e.getMessage());
        }
    }

}
