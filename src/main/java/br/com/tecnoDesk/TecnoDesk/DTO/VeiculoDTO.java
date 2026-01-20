package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Entities.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoDTO {
	
    private long sequencial;

    private String placa;

    private String marca;

    private String modelo;

    private Integer anoFabricacao;

    private Integer anoModelo;

    private String cor;

    private String combustivel;

    private String tipoVeiculo;

    private String chassi;

    private String municipio;

    private String uf;

    private Cliente cliente;

    private String observacoes;

}
