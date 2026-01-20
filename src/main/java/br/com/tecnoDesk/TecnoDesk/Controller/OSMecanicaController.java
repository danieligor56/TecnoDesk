package br.com.tecnoDesk.TecnoDesk.Controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Mecanica;
import br.com.tecnoDesk.TecnoDesk.Services.OSMecanicaService;

@RestController
@RequestMapping("api/v1/os-mecanica")
public class OSMecanicaController {

	@Autowired
	OSMecanicaService osMecanicaService;

	@PostMapping
	public ResponseEntity<OS_Mecanica> criarOSMecanica(@RequestBody OS_Mecanica osMecanica) {
		OS_Mecanica novaOS = osMecanicaService.criarOSMecanica(osMecanica);
		return ResponseEntity.ok(novaOS);
	}

	@GetMapping
	public ResponseEntity<List<OS_Mecanica>> listarOSMecanica() {
		List<OS_Mecanica> osList = osMecanicaService.listarOSMecanica();
		return ResponseEntity.ok(osList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OS_Mecanica> buscarOSMecanicaPorId(@PathVariable long id) {
		Optional<OS_Mecanica> os = osMecanicaService.buscarPorId(id);
		if (os.isPresent()) {
			return ResponseEntity.ok(os.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<OS_Mecanica> atualizarOSMecanica(@PathVariable long id, @RequestBody OS_Mecanica osMecanica) {
		OS_Mecanica osAtualizada = osMecanicaService.atualizarOSMecanica(id, osMecanica);
		return ResponseEntity.ok(osAtualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarOSMecanica(@PathVariable long id) {
		osMecanicaService.deletarOSMecanica(id);
		return ResponseEntity.noContent().build();
	}

}
