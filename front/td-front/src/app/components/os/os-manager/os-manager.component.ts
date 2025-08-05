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
import { TecnicoEPrioridadeDTO } from 'src/app/DTO/TecnicoEPrioridadeDTO';
import { Toast, ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-os-manager',
  templateUrl: './os-manager.component.html',
  styleUrls: ['./os-manager.component.css']
})
export class OsManagerComponent implements OnInit {
  responsalveTecnico:Colaborador = {
    id: null
  };
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
  prioridadeos:string = '';
  numTec:number = 0;
  statusOs: string = ''; 
 

constructor(
    private osService: OsService,
    private route: ActivatedRoute,
    private colaboradorService:ColaboradorService,
    private dialog: MatDialog,
    private orcamentoService:OrcamentoService,
    private toast: ToastrService
  
  ) { }

  ngOnInit(): void {
    debugger;

    



    this.id = this.route.snapshot.paramMap.get('id');
    this.mapearDadosOS(this.id);
    this.dropdownColaborador();
    this.listarItensOrcamento();
 

  }

  mapearDadosOS(id:string){
    debugger;
     return this.osService.findOsByNumOs(Number(id)).subscribe(
      response => {
        this.Os = response;

         if (response.tecnico_responsavel) {
          this.responsalveTecnico = response.tecnico_responsavel;
        } else {
          this.responsalveTecnico = {
            id: null,
            empresa: null,
            nome: null,
            documento: null,
            ocupacao: null,
            email: null,
            cel1: null,
            estado: null,
            bairro: null,
            cidade: null,
            logradouro: null,
            numero: null,
            obs: null,
            cep: null,
            atvReg: null,
          };
          
        }
          // this.responsalveTecnico = response?.tecnico_responsavel;

        switch (response?.prioridadeOS) {
      case "NORMAL":
        this.prioridadeos = "0";
        break;
      case "URGENCIA":
        this.prioridadeos = "1";
        break;
      case "GARANTIA":
        this.prioridadeos = "2";
        break;
      case "PRIORITARIA":
        this.prioridadeos = "3";
        break;
      default:
        this.prioridadeos = "0";
    }

      // this.statusOs = response?.statusOS;

    switch (response?.statusOS){
        case "NOVO":
          this.statusOs = '0';
            break;
        case "EM_ANDAMENTO":
          this.statusOs = '1';
            break;
        case "AGUARDANDO_RESP_ORCAMENTO":
          this.statusOs = '2'
            break;
        case "AGUARDANDO_PECAS":
          this.statusOs = '3';
            break;
        case "AGUARDANDO_RETIRADA":
          this.statusOs = '4';
            break;
        case "ORCAMENTO_APROVADO":
          this.statusOs = '5';
            break;
        case "PENDENTE":
          this.statusOs = '6';
            break;
        case "CONCLUIDO":
          this.statusOs = '7';
            break;
        case "CANCELADA":
          this.statusOs = '8';
            break;
        case "ENCERRADA":
          this.statusOs = '9';
            break;                               

      }

      
        
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
  
  openServicoAvulso(id){
    const dialogRef = this.dialog.open(ItemServiceCreateAvulsoComponent,{
      data:{
        id:id
      },
    });

    dialogRef.afterClosed().subscribe(response => {
      if(response){
        this.listarItensOrcamento();
      }
      
    });
    
  }

  listarItensOrcamento(){
    debugger;
    this.orcamentoService.buscarPorId(Number(this.id)).subscribe( response => {
      this.orcamentoService.listarServicosOrcamento(response.id).subscribe(servicos => {
        this.servico = servicos
        this.dataSource = new MatTableDataSource<OrcamentoItem>(servicos);
        this.FuncValorOrcamento(response.id);
      })
      
    })
    
  }

  FuncValorOrcamento(idOrcamento:number){
   this.orcamentoService.valorOrcamento(idOrcamento).subscribe(response =>{
      this.valorOrcamento = response
   })
  }

  alterarTecnicoEPrioridade(){
    debugger;
    const tecnicoPrioridadeDto: TecnicoEPrioridadeDTO = {
      tecnicoId: this.responsalveTecnico.id, 
      prioridadeOS: Number(this.prioridadeos),
      numOs: this.Os.numOs
    }

    this.osService.alterarTecnicoEPrioridade(tecnicoPrioridadeDto);
    
  }
  
  
}
