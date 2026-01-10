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
import br.com.tecnoDesk.TecnoDesk.Entities.Veiculo;
import br.com.tecnoDesk.TecnoDesk.Services.VeiculoService;

@RestController
@RequestMapping("api/v1/veiculos")
public class VeiculoController {

	@Autowired
	VeiculoService veiculoService;

	@PostMapping
	public ResponseEntity<Veiculo> criarVeiculo(@RequestBody Veiculo veiculo) {
		Veiculo novoVeiculo = veiculoService.criarVeiculo(veiculo);
		return ResponseEntity.ok(novoVeiculo);
	}

	@GetMapping
	public ResponseEntity<List<Veiculo>> listarVeiculos() {
		List<Veiculo> veiculos = veiculoService.listarVeiculos();
		return ResponseEntity.ok(veiculos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Veiculo> buscarVeiculoPorId(@PathVariable long id) {
		Optional<Veiculo> veiculo = veiculoService.buscarPorId(id);
		if (veiculo.isPresent()) {
			return ResponseEntity.ok(veiculo.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Veiculo> atualizarVeiculo(@PathVariable long id, @RequestBody Veiculo veiculo) {
		Veiculo veiculoAtualizado = veiculoService.atualizarVeiculo(id, veiculo);
		return ResponseEntity.ok(veiculoAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarVeiculo(@PathVariable long id) {
		veiculoService.deletarVeiculo(id);
		return ResponseEntity.noContent().build();
	}

}
