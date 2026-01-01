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

    public OsRapida criarOsRapida(OsRapidaDTO dto, String tecnicoResponsavel) throws BadRequest {
        try {
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

            // Set current date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            osRapida.setDataAbertura(formatter.format(now));

            return osRapidaRepository.save(osRapida);

        } catch (Exception e) {
            throw new BadRequest("Não foi possível criar a OS rápida: " + e.getMessage());
        }
    }

    public List<OsRapida> listarOsRapidas() throws Exception {
        try {
            return osRapidaRepository.findAll();
        } catch (Exception e) {
            throw new BadRequest("Não foi possível listar as OS rápidas");
        }
    }

    public List<OsRapida> listarOsRapidasPorTecnico(String tecnico) throws Exception {
        try {
            return osRapidaRepository.findByTecnicoResponsavel(tecnico);
        } catch (Exception e) {
            throw new BadRequest("Não foi possível listar as OS rápidas do técnico");
        }
    }

}
