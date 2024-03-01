package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.DTO.ColaboradorDTO;
import br.com.tecnoDesk.TecnoDesk.Entities.Colaborador;
import br.com.tecnoDesk.TecnoDesk.Repository.ColaboradorRespository;
import lombok.var;

@Service

public class ColaboradorService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ColaboradorRespository colaboradorRespository;
	
	
	public Colaborador adicionaColaborador(ColaboradorDTO colaboradorDTO) {
		
		var addNovCob = modelMapper.map(colaboradorDTO, Colaborador.class);
		colaboradorRespository.save(addNovCob);
		return null;
		
	}
	
	public List<Colaborador> encontraColaboradorPNome(String nome) {

		return colaboradorRespository.buscarPornome(nome);

	}
	
	public List<Colaborador> listarColaboradores(){
		return colaboradorRespository.findAll();
	}
	

}
