package br.com.tecnoDesk.TecnoDesk.Services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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
	return osRepository.save(novaOs);
	}
	

}
