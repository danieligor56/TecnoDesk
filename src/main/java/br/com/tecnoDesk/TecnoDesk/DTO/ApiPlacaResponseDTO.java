package br.com.tecnoDesk.TecnoDesk.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPlacaResponseDTO {
	
		private String placa;
		private String marca;	    
		private String modelo;
	    private String cor;
	    private String ano;
	    private String anoModelo;
	    private String chassi;
	    private String municipio;
	    private String uf;
	    private String logo;
}
