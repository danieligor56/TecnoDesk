import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ItemService } from 'src/app/models/ItemService';
import { ItemServiceService } from 'src/app/services/item-service.service';
import { ItemServiceCreateComponent } from '../item-service-create/item-service-create.component';
import { OrcamentoItem } from 'src/app/models/OrcamentoItem';
import { OrcamentoService } from 'src/app/services/orcamento.service';
import { ItemServiceCobrarhoraComponent } from '../item-service-cobrarhora/item-service-cobrarhora.component';

@Component({
  selector: 'app-item-servicel-minilist',
  templateUrl: './item-servicel-minilist.component.html',
  styleUrls: ['./item-servicel-minilist.component.css']
})
export class ItemServicelMinilistComponent implements OnInit {
servico: ItemService [] = [];
displayedColumns: string[] = ['id', 'progress','fruit','name','add'];
dataSource = new MatTableDataSource<ItemService>(this.servico);
servicoContrato: OrcamentoItem;
codOrcamento:number = 0;
valorHorasServico:number = 0;

@ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private itemService:ItemServiceService,
    private dialog:MatDialog,
    private dialogRef: MatDialogRef<ItemServicelMinilistComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private  orcamentoService:OrcamentoService
    
    
  ) { }

  ngOnInit(): void {
    this.encontrarServicos();
  }

  encontrarServicos(){
      this.itemService.listServico().subscribe(response => {
        this.servico = response;
            this.dataSource = new MatTableDataSource<ItemService>(response);
            this.dataSource.paginator = this.paginator;
            
          }
      )
      }

  applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    
          if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
          }
        }
  closeDialog(){
    this.dialogRef.close();
    }
 
  
    openCreatServiceDialog(){
    
      const dialogRef = this.dialog.open(ItemServiceCreateComponent);
      dialogRef.afterClosed().subscribe(response => {
        if(response)
          this.encontrarServicos();
      })
  }


  adicionarServico(id: number,cobrarPorUnd:boolean) {
   
    // Primeiro: buscar o orçamento
    this.orcamentoService.buscarPorId(this.data.id).subscribe(orcamento => {
      this.codOrcamento = orcamento.id;
  
      // Segundo: buscar o item
      this.itemService.encontrarPorId(id).subscribe(response => {
        this.servicoContrato = {
          empresa: response.empresa,
          codOrcamento: this.codOrcamento,
          codigoItem: Number(response.id),
          nomeServicoAvulso: response.nomeServico,        
          descricaoServicoAvulso: response.descricaoServico,
          // valorUnidadeAvulso: response.valorServicoUnidade,
          // valorHoraAvulso: 0,
          isAvulso: false
        };

        if(cobrarPorUnd){
          this.servicoContrato.valorUnidadeAvulso = response.valorServicoUnidade;
          this.servicoContrato.valorHoraAvulso = 0;
        } else{
          this.servicoContrato.valorHoraAvulso = this.valorHorasServico;
          this.servicoContrato.valorUnidadeAvulso = 0
        }
        
        debugger;
        // Terceiro: enviar para o backend
        this.orcamentoService.inserirItem(this.codOrcamento, this.servicoContrato)
        this.dialogRef.close(true);
      });
    });
  }

  cobrarHoraDialog(valorServicoHora,idServico){

    const dialogRef = this.dialog.open(ItemServiceCobrarhoraComponent,{
      data:{
        valorServicoHora:valorServicoHora
      }
    })
     dialogRef.afterClosed().subscribe(result => {
      if(result > 0){
        this.valorHorasServico = result;
        this.adicionarServico(idServico,false)
      }
     })
   } 

}
