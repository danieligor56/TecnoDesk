package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.DTO.UnidadeMedidaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.UnidadeMedida;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.UnidadeMedidaRepository;
import exception.BadRequest;
import exception.NotFound;

@Service
public class UnidadeMedidaService {

    @Autowired
    private UnidadeMedidaRepository repository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DecriptService decriptService;

    public List<UnidadeMedidaDTO> listarPorEmpresa(String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        verificarPadroes(codEmp);
        return repository.findByEmpresaId(codEmp).stream()
                .map(u -> new UnidadeMedidaDTO(u.getId(), u.getNome(), u.getSigla(), u.getEmpresa().getId()))
                .collect(Collectors.toList());
    }

    public UnidadeMedidaDTO criar(UnidadeMedidaDTO dto, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        Empresa empresa = empresaRepository.findById(codEmp)
                .orElseThrow(() -> new NotFound("Empresa não encontrada"));

        UnidadeMedida unidade = new UnidadeMedida();
        unidade.setNome(dto.nome());
        unidade.setSigla(dto.sigla());
        unidade.setEmpresa(empresa);

        unidade = repository.save(unidade);
        return new UnidadeMedidaDTO(unidade.getId(), unidade.getNome(), unidade.getSigla(),
                unidade.getEmpresa().getId());
    }

    public UnidadeMedidaDTO atualizar(Long id, UnidadeMedidaDTO dto, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        UnidadeMedida unidade = repository.findById(id)
                .orElseThrow(() -> new NotFound("Unidade de medida não encontrada"));

        if (unidade.getEmpresa().getId() != codEmp) {
            throw new BadRequest("Unidade de medida não pertence a esta empresa");
        }

        unidade.setNome(dto.nome());
        unidade.setSigla(dto.sigla());

        unidade = repository.save(unidade);
        return new UnidadeMedidaDTO(unidade.getId(), unidade.getNome(), unidade.getSigla(),
                unidade.getEmpresa().getId());
    }

    public void excluir(Long id, String codEmpresaCript) throws Exception {
        Long codEmp = Long.valueOf(decriptService.decriptCodEmp(codEmpresaCript));
        UnidadeMedida unidade = repository.findById(id)
                .orElseThrow(() -> new NotFound("Unidade de medida não encontrada"));

        if (unidade.getEmpresa().getId() != codEmp) {
            throw new BadRequest("Unidade de medida não pertence a esta empresa");
        }

        try {
            repository.delete(unidade);
        } catch (Exception e) {
            throw new BadRequest("Não é possível excluir a unidade de medida pois ela pode estar em uso.");
        }
    }

    public void verificarPadroes(Long empresaId) {
        List<UnidadeMedida> existentes = repository.findByEmpresaId(empresaId);
        if (existentes.isEmpty()) {
            Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
            if (empresa != null) {
                repository.save(new UnidadeMedida(null, "Unidade", "UN", empresa));
                repository.save(new UnidadeMedida(null, "Kilograma", "KG", empresa));
                repository.save(new UnidadeMedida(null, "Peça", "PC", empresa));
            }
        }
    }
}
