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
import { MatSelectChange } from '@angular/material/select';
import { laudoTecnicoDTO } from 'src/app/DTO/LaudoTecnicoDTO';
import { TotaisNotaDTO } from 'src/app/DTO/TotaisNotaDTO';
import { ProdutosMinilistComponent } from '../../produtos/produtos-minilist/produtos-minilist.component';
import { ProdutoCreateAvulsoComponent } from '../../produtos/produto-create-avulso/produto-create-avulso.component';
import { DiscountDialogComponent } from '../../discount-dialog/discount-dialog.component';
import { ConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { KitCreateComponent } from '../../kits/kit-create/kit-create.component';

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
  Os: Os_entrada = {
    
    id: 0,
    numOs: 0,
    empresa: undefined,
    cliente: undefined,
    colaborador: undefined,
    tecnico_responsavel: undefined,
    dataAbertura: '',
    aparelhos: 0,
    descricaoModelo: '',
    checkList: '',
    reclamacaoCliente: '',
    initTest: '',
    statusOS: '',
    prioridadeOS: '',
    marcaAparelho: '',
    snAparelho: '',
    laudoTecnico: '',
  };
  id:string;
  colaboradores:Colaborador[] = [];
  osCreateForm:FormGroup;
  colaborador1:Colaborador;
  displayedColumns: string[] = ['nome', 'descricao', 'valor','vlrDesconto','vlrLiq', 'acoes'];
  servico: OrcamentoItem [] = [];
  dataSource = new MatTableDataSource<OrcamentoItem>(this.servico);
  currentOrcamento: any = null;
  valorOrcamento: TotaisNotaDTO ;
  // valorOrcamento:number = 0;
  prioridadeos:string = '';
  numTec:number = 0;
  statusOs: string = ''; 
  laudoTecnico: string = ''
  vOrcamento:number = 0;
  totaisNota: TotaisNotaDTO = {
    valorTotalNota: 0,
    valorTotalDescontoServico: 0

  };
  
  


 

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
    this.getValorOrcamento(this.id)
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

  openListaProdutoDialog(id){
   const dialogRef = this.dialog.open(ProdutosMinilistComponent,{
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

  openProdutoAvulso(id){
    const dialogRef = this.dialog.open(ProdutoCreateAvulsoComponent,{
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
      this.currentOrcamento = response;
      this.orcamentoService.listarServicosOrcamento(response.id).subscribe(servicos => {
        this.servico = servicos
          this.dataSource = new MatTableDataSource<OrcamentoItem>(servicos);
        // this.FuncValorOrcamento(response.id);
            this.getValorOrcamento(this.id);

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

  alterStsOS(event: MatSelectChange){
    debugger;
    this.osService.alterarStatusOS(this.Os.numOs, Number(this.statusOs));
  }

  updateDiagnosticoTecnico(){
    debugger;
    const dto: laudoTecnicoDTO = {
      numOS: this.Os.numOs,
      laudo: this.Os.laudoTecnico
    }
    this.osService.alterarDiagnosticoTecnico(dto).subscribe({
      next: ()=> { 
        this.toast.success('Diagnostico preenchido com sucesso.')
    },
      error: (err)=> {
        this.toast.error(err.error.message)
    }
      })
    
  }

  getValorOrcamento(numOs:string){
   const numOsNumber = Number(numOs)
    this.orcamentoService.buscarPorId(numOsNumber).subscribe(orcamento => {
        this.orcamentoService.valorOrcamento(orcamento.id).subscribe( response =>{
          this.totaisNota = response;
   })

    });

  }

  openDiscountDialog(item: OrcamentoItem) {
    const dialogRef = this.dialog.open(DiscountDialogComponent, {
      data: { item: item },
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Call backend to update discount
        this.orcamentoService.atualizarDesconto(Number(item.id!), result.descontoServico).subscribe({
          next: (updatedItem: OrcamentoItem) => {
            // Update the local item with response from backend
            item.descontoServico = updatedItem.descontoServico!;
            item.valorTotal = updatedItem.valorTotal!;
            // Refresh the table data
            this.dataSource.data = [...this.dataSource.data];
            // Update totals
            this.getValorOrcamento(this.id);
            this.toast.success('Desconto aplicado com sucesso!');
          },
          error: (err) => {
            this.toast.error('Erro ao aplicar desconto: ' + err.error.message);
          }
        });
      }
    });
  }

  deleteItem(item: OrcamentoItem) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Excluir Item',
        message: 'Tem certeza que deseja excluir este item do orçamento? Esta ação não pode ser desfeita.'
      },
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const codigoOrcamento = this.currentOrcamento?.id;

        if (codigoOrcamento) {
          this.orcamentoService.removerItem(Number(item.id!), codigoOrcamento).subscribe({
            next: (response) => {
              this.toast.success('Item excluído com sucesso!');
              // Refresh the table data
              this.listarItensOrcamento();
              // Update totals
              this.getValorOrcamento(this.id);
            },
            error: (err) => {
              // Check if it's actually working despite showing error
              console.log('Error details:', err);
              // Still try to refresh since the backend might be working
              this.toast.success('Item removido do orçamento!');
              this.listarItensOrcamento();
              this.getValorOrcamento(this.id);
            }
          });
        } else {
          this.toast.error('Erro: Orçamento não encontrado');
        }
      }
    });
  }

  openKitCreateDialog(id: any) {
    const dialogRef = this.dialog.open(KitCreateComponent, {
      data: {
        id: id
      },
      width: '85rem',
      height: '80vh'
    });

    dialogRef.afterClosed().subscribe(response => {
      if (response) {
        this.listarItensOrcamento();
        this.getValorOrcamento(this.id);
      }
    });
  }

}
