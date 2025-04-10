package br.com.tecnoDesk.TecnoDesk.Entities;

import jakarta.persistence.GeneratedValue;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "OrcamentoItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrcamentoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
	@JoinColumn(name = "codigo_empresa",nullable = false)
	private Empresa empresa;
    
    @ManyToOne
	@JoinColumn(name = "codigo_orcamento",nullable = true)
	private Orcamento orcamento;
    
    @Column(name = "codigo_item",nullable = true)
    private long codigoItem;
    
    @Column(name = "nome_servico")
    private String nomeServicoAvulso;

    @Column(name = "descricao_servico")
    private String descricaoServicoAvulso;

    @Column(name = "valor_unidade")
    private Double valorUnidadeAvulso;

    @Column(name = "valor_hora")
    private Double valorHoraAvulso;
    
    @Column(name = "isAvulso")
    private boolean isAvulso;
    
    
}
