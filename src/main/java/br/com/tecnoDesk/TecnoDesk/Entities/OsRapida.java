package br.com.tecnoDesk.TecnoDesk.Entities;

import java.math.BigDecimal;
import java.util.Date;

import br.com.tecnoDesk.TecnoDesk.Enuns.StatusOS;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "os_rapida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OsRapida {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // Identificação simples do cliente
	    @Column(name = "cliente_nome", nullable = false, length = 120)
	    private String clienteNome;

	    @Column(name = "cliente_telefone", length = 20)
	    private String clienteTelefone;

	    // Equipamento ou serviço
	    @Column(name = "equipamento_servico", nullable = false, length = 150)
	    private String equipamentoServico;

	    // Problema relatado
	    @Column(name = "problema_relatado", nullable = false, length = 255)
	    private String problemaRelatado;

	    // Status da OS
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false, length = 30)
	    private StatusOS status;

	    // Valores opcionais
	    @Column(name = "valor_estimado", precision = 10, scale = 2)
	    private BigDecimal valorEstimado;

	    @Column(name = "prazo_combinado", length = 50)
	    private String prazoCombinado;

	    // Observações internas
	    @Column(length = 500)
	    private String observacoes;

	    // Técnico responsável (usuário logado)
	    @Column(name = "tecnico_responsavel", nullable = false, length = 100)
	    private String tecnicoResponsavel;

	    // Datas
	    @Column(name = "data_abertura", nullable = false)
	    private String dataAbertura;

	  

}
