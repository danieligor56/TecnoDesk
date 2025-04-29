import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { OrcamentoItem } from 'src/app/models/OrcamentoItem';
import { ItemServiceService } from 'src/app/services/item-service.service';
import { OrcamentoService } from 'src/app/services/orcamento.service';


@Component({
  selector: 'app-item-service-cobrarhora',
  templateUrl: './item-service-cobrarhora.component.html',
  styleUrls: ['./item-service-cobrarhora.component.css']
})
export class ItemServiceCobrarhoraComponent implements OnInit {
    quantidadeHoras = '';
    valorServico = this.data.valorServicoHora;
    vTotal = 0;
    codOrcamento:number = 0;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { valorServicoHora: number},
    // private orcamentoService:OrcamentoService,
    // private itemService:ItemServiceService,
    // private servicoContrato: OrcamentoItem,
    private dialogRef: MatDialogRef<ItemServiceCobrarhoraComponent>, 

  ) { }

  ngOnInit(): void {
  }

  calcValorPorHora() {
    debugger;
    const horas = Number(this.quantidadeHoras) || 0;
    const valor = Number(this.valorServico) || 0;
  
    this.vTotal = valor * horas;
  }

  retornarValorHora(){
    this.dialogRef.close(this.vTotal)
  }

  // adicionarServico(id: number) {
   
  //   // Primeiro: buscar o orçamento
  //   this.orcamentoService.buscarPorId(Number(this.data.id)).subscribe(orcamento => {
  //     this.codOrcamento = orcamento.id;
  
  //     // Segundo: buscar o item
  //     this.itemService.encontrarPorId(id).subscribe(response => {
  //       this.servicoContrato = {
  //         empresa: response.empresa,
  //         codOrcamento: this.codOrcamento,
  //         codigoItem: Number(response.id),
  //         nomeServicoAvulso: response.nomeServico,
  //         descricaoServicoAvulso: response.descricaoServico,
  //         valorUnidadeAvulso: 0,
  //         valorHoraAvulso: this.vTotal,
  //         isAvulso: false
  //       };
  //       debugger;
  //       // Terceiro: enviar para o backend
  //       this.orcamentoService.inserirItem(this.codOrcamento, this.servicoContrato)
  //       this.dialogRef.close(true);
  //     });
  //   });
  // }
  

}
