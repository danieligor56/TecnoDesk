package br.com.tecnoDesk.TecnoDesk.Services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.tecnoDesk.TecnoDesk.DTO.OS_EntradaDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import br.com.tecnoDesk.TecnoDesk.Repository.ClienteRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.ColaboradorRespository;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;

@Service
public class OsService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	OsRepository osRepository;
	
	@Autowired
	ColaboradorRespository colaboradorRespository;
	
	@Autowired
	DecriptService decriptService;
	
	@Autowired
	EmpresaRepository empresaRepository;

	public OS_Entrada crianova(OS_EntradaDTO osDTO,String codEmpresa) throws BadRequestException {
			
		
		try {
			
			Empresa empresa = empresaRepository.findEmpresaById(Long.valueOf(codEmpresa));
			
			OS_Entrada novaOs = modelMapper.map(osDTO, OS_Entrada.class);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate now = LocalDate.now();
			formatter.format(now);
			novaOs.setDataAbertura(formatter.format(now));
			novaOs.setEmpresa(empresa);
			osRepository.save(novaOs);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
		

	}

}
