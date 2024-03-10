package br.com.tecnoDesk.TecnoDesk.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.DTO.OsDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.OS;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;

@Service
public class OsService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	OsRepository osRepository;
		
	public OS crianova(OsDTO osDTO) throws BadRequestException {
	
	if(osDTO.getColaborador().getOcupacao() != Ocupacao.TECNICO) {
		
	throw new BadRequestException("O colaborador selecionado não é um técnico");
	
	}
	
	OS novaOs =	modelMapper.map(osDTO, OS.class);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	LocalDate now = LocalDate.now();
	formatter.format(now);
	novaOs.setDataAbertura(formatter.format(now));
	osRepository.save(novaOs);
	
	return null;
	}
	
	

}