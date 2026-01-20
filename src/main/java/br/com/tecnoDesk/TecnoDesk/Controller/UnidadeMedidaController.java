package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.tecnoDesk.TecnoDesk.DTO.UnidadeMedidaDTO;
import br.com.tecnoDesk.TecnoDesk.Services.UnidadeMedidaService;

@RestController
@RequestMapping("api/v1/unidades-medida")
public class UnidadeMedidaController {

    @Autowired
    private UnidadeMedidaService service;

    @GetMapping
    public ResponseEntity<List<UnidadeMedidaDTO>> list(@RequestHeader("CodEmpresa") String codEmpresa)
            throws Exception {
        return ResponseEntity.ok(service.listarPorEmpresa(codEmpresa));
    }

    @PostMapping
    public ResponseEntity<UnidadeMedidaDTO> create(@RequestBody UnidadeMedidaDTO dto,
            @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok(service.criar(dto, codEmpresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeMedidaDTO> update(@PathVariable Long id, @RequestBody UnidadeMedidaDTO dto,
            @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok(service.atualizar(id, dto, codEmpresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("CodEmpresa") String codEmpresa)
            throws Exception {
        service.excluir(id, codEmpresa);
        return ResponseEntity.noContent().build();
    }
}
