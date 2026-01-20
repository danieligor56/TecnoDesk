package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.tecnoDesk.TecnoDesk.DTO.CategoriaProdutoDTO;
import br.com.tecnoDesk.TecnoDesk.Services.CategoriaProdutoService;

@RestController
@RequestMapping("api/v1/categorias-produto")
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoService service;

    @GetMapping
    public ResponseEntity<List<CategoriaProdutoDTO>> list(@RequestHeader("CodEmpresa") String codEmpresa)
            throws Exception {
        return ResponseEntity.ok(service.listarPorEmpresa(codEmpresa));
    }

    @PostMapping
    public ResponseEntity<CategoriaProdutoDTO> create(@RequestBody CategoriaProdutoDTO dto,
            @RequestHeader("CodEmpresa") String codEmpresa) throws Exception {
        return ResponseEntity.ok(service.criar(dto, codEmpresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProdutoDTO> update(@PathVariable Long id, @RequestBody CategoriaProdutoDTO dto,
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
