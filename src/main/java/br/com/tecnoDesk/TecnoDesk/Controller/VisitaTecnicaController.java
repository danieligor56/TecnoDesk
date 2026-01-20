package br.com.tecnoDesk.TecnoDesk.Controller;

import br.com.tecnoDesk.TecnoDesk.DTO.VisitaTecnicaDTO;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusVisita;
import br.com.tecnoDesk.TecnoDesk.Services.VisitaTecnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/visitas-tecnicas")
public class VisitaTecnicaController {

    @Autowired
    private VisitaTecnicaService service;

    @GetMapping
    public ResponseEntity<List<VisitaTecnicaDTO>> listar(@RequestHeader("CodEmpresa") String codEmpresa)
            throws Exception {
        return ResponseEntity.ok(service.listarPorEmpresa(codEmpresa));
    }

    @PostMapping
    public ResponseEntity<VisitaTecnicaDTO> criar(@RequestBody VisitaTecnicaDTO dto,
            @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok(service.criar(dto, codEmpresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitaTecnicaDTO> atualizar(@PathVariable Long id, @RequestBody VisitaTecnicaDTO dto,
            @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok(service.atualizar(id, dto, codEmpresa));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<VisitaTecnicaDTO> alterarStatus(@PathVariable Long id, @RequestParam StatusVisita status,
            @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok(service.alterarStatus(id, status, codEmpresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id, @RequestHeader("CodEmpresa") String codEmpresa)
            throws Exception {
        service.excluir(id, codEmpresa);
        return ResponseEntity.noContent().build();
    }
}
