package br.com.tecnoDesk.TecnoDesk.Services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.Config.ModdelMapperConf;
import br.com.tecnoDesk.TecnoDesk.DTO.ColaboradorDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Repository.ColaboradorRespository;
import lombok.var;

@Service

public class colaboradorService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ColaboradorRespository colaboradorRespository;
	
	
	public Colaborador adicionaColaborador(ColaboradorDTO colaboradorDTO) {
		
		var addNovCob = modelMapper.map(colaboradorDTO, Colaborador.class);
		colaboradorRespository.save(addNovCob);
		return null;
		
	}
	

}
