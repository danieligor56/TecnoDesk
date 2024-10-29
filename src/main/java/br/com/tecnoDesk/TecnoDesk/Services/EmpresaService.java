package br.com.tecnoDesk.TecnoDesk.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	public Empresa buscarEmpresoPorID(long codEmp) {
		return empresaRepository.findEmpresaById(codEmp);
	}

}
