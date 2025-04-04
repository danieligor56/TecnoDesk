package br.com.tecnoDesk.TecnoDesk.DTO;

import java.util.List;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Enuns.Ocupacao;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicoDTO {
	
	private Empresa empresa;
	
	
	private String nomeServico;
	
	
	private String descricaoServico;
	
	
	private Double valorServicoUnidade;
	
	
	private Double valorServicoHora;

}
