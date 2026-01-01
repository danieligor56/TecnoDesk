package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecnoDesk.TecnoDesk.DTO.OsRapidaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.OsRapida;
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
                                                 @RequestHeader("TecnicoResponsavel") String tecnicoResponsavel) throws BadRequest {
        return ResponseEntity.ok().body(osRapidaService.criarOsRapida(dto, tecnicoResponsavel));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<OsRapida>> listarOsRapidas() throws Exception {
        return ResponseEntity.ok().body(osRapidaService.listarOsRapidas());
    }

    @GetMapping("/listarPorTecnico")
    public ResponseEntity<List<OsRapida>> listarOsRapidasPorTecnico(@RequestHeader("TecnicoResponsavel") String tecnico) throws Exception {
        return ResponseEntity.ok().body(osRapidaService.listarOsRapidasPorTecnico(tecnico));
    }

}
