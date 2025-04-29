import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Colaborador } from 'src/app/models/Colaborador';
import { Os_entrada } from 'src/app/models/Os-entrada';
import { ColaboradorService } from 'src/app/services/colaborador.service';
import { OsService } from 'src/app/services/os.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ItemServicelMinilistComponent } from '../../item-service/item-servicel-minilist/item-servicel-minilist.component';
import { ItemServiceCreateAvulsoComponent } from '../../item-service/item-service-create-avulso/item-service-create-avulso.component';
import { OrcamentoService } from 'src/app/services/orcamento.service';
import { MatTableDataSource } from '@angular/material/table';
import { OrcamentoItem } from 'src/app/models/OrcamentoItem';

@Component({
  selector: 'app-os-manager',
  templateUrl: './os-manager.component.html',
  styleUrls: ['./os-manager.component.css']
})
export class OsManagerComponent implements OnInit {
  [x: string]: any;
  nomeCliente:string = '';
  Os:Os_entrada;
  id:string;
  colaboradores:Colaborador[] = [];
  osCreateForm:FormGroup;
  colaborador1:Colaborador;
  displayedColumns: string[] = ['nome', 'descricao', 'valor', 'acoes'];
  servico: OrcamentoItem [] = [];
  dataSource = new MatTableDataSource<OrcamentoItem>(this.servico);
  valorOrcamento:number = 0;
  
constructor(
    private osService: OsService,
    private route: ActivatedRoute,
    private colaboradorService:ColaboradorService,
    private dialog: MatDialog,
    private orcamentoService:OrcamentoService
  ) { }

  ngOnInit(): void {
    debugger;
    this.id = this.route.snapshot.paramMap.get('id');
    this.mapearDadosOS(this.id)
    this.dropdownColaborador();
    this.listarItensOrcamento();
    this.FuncValorOrcamento();
 

  }

  mapearDadosOS(id:string){
    
     return this.osService.findOsByNumOs(Number(id)).subscribe(
      response => {
        this.Os = response;
      })

  }

  dropdownColaborador(){
    debugger;
    this.colaboradorService.listarTecnicos().subscribe(
      (response) => {
        this.colaboradores = response
    }),

    (error) => {
      console.error("Não foi possível carregar os colaboradores.")
    }

  };

  onColaboradorChange(colaboradorId: number) {
    debugger;
    this.osCreateForm.patchValue({
      colaborador: { id: colaboradorId }
    });
  }

  openListaServicoDialog(id){
   const dialogRef = this.dialog.open(ItemServicelMinilistComponent,{
    data:{
        id:id
    },
    width:'50rem'
    
   });
    dialogRef.afterClosed().subscribe(response => {
    if(response){
      this.listarItensOrcamento();
    }

  });

  }
  
  openServicoAvulso(){
    this.dialog.open(ItemServiceCreateAvulsoComponent);
  }

  listarItensOrcamento(){
    debugger;
    this.orcamentoService.buscarPorId(Number(this.id)).subscribe( response => {
      this.orcamentoService.listarServicosOrcamento(response.id).subscribe(servicos => {
        this.servico = servicos
        this.dataSource = new MatTableDataSource<OrcamentoItem>(servicos);
        this.FuncValorOrcamento();
      })
      
    })
    
  }

  FuncValorOrcamento(){
    debugger;
    this.servico.forEach(calc => {
      if(calc.valorUnidadeAvulso != 0){
        this.valorOrcamento += calc.valorUnidadeAvulso;
      } else {
        this.valorOrcamento += calc.valorHoraAvulso;
      }
    });
  }
  
  
}
