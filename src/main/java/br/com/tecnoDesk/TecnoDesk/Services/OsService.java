package br.com.tecnoDesk.TecnoDesk.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.DTO.OsDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.OS;
import br.com.tecnoDesk.TecnoDesk.Repository.OsRepository;

@Service
public class OsService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	OsRepository osRepository;
		
	public OS crianova(OsDTO osDTO) {
	OS novaOs =	modelMapper.map(osDTO, OS.class);
	LocalDate now = LocalDate.now(); 
	novaOs.setDataAbertura(now);
	osRepository.save(novaOs);
	return null;
	}
	

}
