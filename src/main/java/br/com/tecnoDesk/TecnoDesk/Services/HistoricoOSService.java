package br.com.tecnoDesk.TecnoDesk.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.Entities.HistoricoOS;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.Usuarios;
import br.com.tecnoDesk.TecnoDesk.Repository.HistoricoOSRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.UsuarioRepository;

@Service
public class HistoricoOSService {

    @Autowired
    private HistoricoOSRepository historicoOSRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DecriptService decriptService;

    public void registrar(OS_Entrada os, String descricao) {
        String emailResponavel = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuarios usuario = usuarioRepository.findItByEmail(emailResponavel);
        String nomeResponsavel = usuario != null ? usuario.getNomeCompleto() : "Sistema";

        HistoricoOS historico = new HistoricoOS();
        historico.setOs(os);
        historico.setDescricao(descricao);
        historico.setResponsavel(nomeResponsavel);
        historico.setEmpresa(os.getEmpresa());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        historico.setDataAlteracao(LocalDateTime.now().format(formatter));

        historicoOSRepository.save(historico);
    }

    public List<HistoricoOS> listarPorOs(long osSequencial, String codEmpresa) throws Exception {
        Long empresaId = decriptService.decriptCodEmp(codEmpresa);
        return historicoOSRepository.findByOsSequencialAndEmpresaIdOrderByDataAlteracaoDesc(osSequencial, empresaId);
    }
}
