package br.com.tecnoDesk.TecnoDesk.DTO;

import java.util.ArrayList;
import java.util.List;
import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import br.com.tecnoDesk.TecnoDesk.Entities.OS_Entrada;
import br.com.tecnoDesk.TecnoDesk.Entities.OrcamentoItem;
import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOR;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrcamentoDTO {

	private Empresa empresa;
	
	private OS_Entrada os;
	
	private List<OrcamentoItem> itens = new ArrayList<>();
	
	private StatusOR statusOR;
}
