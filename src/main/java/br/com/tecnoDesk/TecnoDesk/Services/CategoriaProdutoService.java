package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.DTO.CategoriaProdutoDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.CategoriaProduto;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.CategoriaProdutoRepository;
import exception.BadRequest;
import exception.NotFound;

@Service
public class CategoriaProdutoService {

    @Autowired
    private CategoriaProdutoRepository repository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DecriptService decriptService;

    public List<CategoriaProdutoDTO> listarPorEmpresa(String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        verificarPadroes(codEmp);
        return repository.findByEmpresaId(codEmp).stream()
                .map(c -> new CategoriaProdutoDTO(c.getId(), c.getNome(), c.getEmpresa().getId()))
                .collect(Collectors.toList());
    }

    public CategoriaProdutoDTO criar(CategoriaProdutoDTO dto, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        Empresa empresa = empresaRepository.findById(codEmp)
                .orElseThrow(() -> new NotFound("Empresa não encontrada"));

        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setNome(dto.nome());
        categoria.setEmpresa(empresa);

        categoria = repository.save(categoria);
        return new CategoriaProdutoDTO(categoria.getId(), categoria.getNome(), categoria.getEmpresa().getId());
    }

    public CategoriaProdutoDTO atualizar(Long id, CategoriaProdutoDTO dto, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        CategoriaProduto categoria = repository.findById(id)
                .orElseThrow(() -> new NotFound("Categoria não encontrada"));

        if (categoria.getEmpresa().getId() != codEmp) {
            throw new BadRequest("Categoria não pertence a esta empresa");
        }

        categoria.setNome(dto.nome());

        categoria = repository.save(categoria);
        return new CategoriaProdutoDTO(categoria.getId(), categoria.getNome(), categoria.getEmpresa().getId());
    }

    public void excluir(Long id, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        CategoriaProduto categoria = repository.findById(id)
                .orElseThrow(() -> new NotFound("Categoria não encontrada"));

        if (categoria.getEmpresa().getId() != codEmp) {
            throw new BadRequest("Categoria não pertence a esta empresa");
        }

        try {
            repository.delete(categoria);
        } catch (Exception e) {
            throw new BadRequest("Não é possível excluir a categoria pois ela pode estar em uso.");
        }
    }

    public void verificarPadroes(Long empresaId) {
        List<CategoriaProduto> existentes = repository.findByEmpresaId(empresaId);
        if (existentes.isEmpty()) {
            Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
            if (empresa != null) {
                repository.save(new CategoriaProduto(null, "Geral", empresa));
                repository.save(new CategoriaProduto(null, "Diversos", empresa));
                repository.save(new CategoriaProduto(null, "Outros", empresa));
            }
        }
    }
}
