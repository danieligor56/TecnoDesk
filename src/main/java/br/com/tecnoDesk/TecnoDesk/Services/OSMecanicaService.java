package br.com.tecnoDesk.TecnoDesk.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Mecanica;
import br.com.tecnoDesk.TecnoDesk.Repository.OSMecanicaRepository;
import exception.NotFound;

@Service
public class OSMecanicaService {

	@Autowired
	OSMecanicaRepository osMecanicaRepository;

	public OS_Mecanica criarOSMecanica(OS_Mecanica osMecanica) {
		if (osMecanica.getVeiculo() == null) {
			throw new IllegalArgumentException("Veículo é obrigatório na OS Mecânica");
		}
		return osMecanicaRepository.save(osMecanica);
	}

	public List<OS_Mecanica> listarOSMecanica() {
		return osMecanicaRepository.findAll();
	}

	public Optional<OS_Mecanica> buscarPorId(long id) {
		return osMecanicaRepository.findById(id);
	}

	public OS_Mecanica atualizarOSMecanica(long id, OS_Mecanica osMecanica) {
		if (!osMecanicaRepository.existsById(id)) {
			throw new NotFound("OS Mecânica não encontrada");
		}
		osMecanica.setId(id);
		return osMecanicaRepository.save(osMecanica);
	}

	public void deletarOSMecanica(long id) {
		if (!osMecanicaRepository.existsById(id)) {
			throw new NotFound("OS Mecânica não encontrada");
		}
		osMecanicaRepository.deleteById(id);
	}

}
