package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.DTO.OsRapidaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.OsRapida;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import br.com.tecnoDesk.TecnoDesk.Services.OsRapidaService;
import exception.BadRequest;

@Controller
@RequestMapping("/OsRapida")
@RestController
public class OsRapidaController {

    @Autowired
    OsRapidaService osRapidaService;

    @PostMapping("/criar")
    public ResponseEntity<OsRapida> criarOsRapida(@RequestBody OsRapidaDTO dto,
                                                 @RequestHeader("TecnicoResponsavel") String tecnicoResponsavel,
                                                 @RequestHeader("CodEmpresa") String codEmpresa) throws BadRequest {
        return ResponseEntity.ok().body(osRapidaService.criarOsRapida(dto, tecnicoResponsavel, codEmpresa));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<OsRapida>> listarOsRapidas(@RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok().body(osRapidaService.listarOsRapidas(codEmpresa));
    }

    @GetMapping("/listarPorTecnico")
    public ResponseEntity<List<OsRapida>> listarOsRapidasPorTecnico(@RequestHeader("TecnicoResponsavel") String tecnico,
                                                                   @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok().body(osRapidaService.listarOsRapidasPorTecnico(tecnico, codEmpresa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OsRapida> buscarOsRapidaPorId(@PathVariable Long id,
                                                       @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok().body(osRapidaService.buscarOsRapidaPorId(id, codEmpresa));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<OsRapida> atualizarOsRapida(@PathVariable Long id,
                                                     @RequestBody OsRapidaDTO dto,
                                                     @RequestHeader("TecnicoResponsavel") String tecnicoResponsavel,
                                                     @RequestHeader("CodEmpresa") String codEmpresa) throws BadRequest {
        return ResponseEntity.ok().body(osRapidaService.atualizarOsRapida(id, dto, tecnicoResponsavel, codEmpresa));
    }

    @PutMapping("/encerrar/{id}")
    public ResponseEntity<Void> encerrarOsRapida(@PathVariable Long id,
                                                @RequestHeader("CodEmpresa") String codEmpresa) throws BadRequest {
        osRapidaService.alterarStatusOsRapida(id, StatusOS.ENCERRADA, codEmpresa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelarOsRapida(@PathVariable Long id,
                                                @RequestHeader("CodEmpresa") String codEmpresa) throws BadRequest {
        osRapidaService.alterarStatusOsRapida(id, StatusOS.CANCELADA, codEmpresa);
        return ResponseEntity.ok().build();
    }

}
