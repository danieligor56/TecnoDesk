import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ClienteService } from 'src/app/services/cliente.service';
import { ClienteCreateComponent } from '../../clientes/cliente-create/cliente-create.component';
import { ClienteCreateOsComponent } from '../cliente-create-os/cliente-create-os.component';
import { CancelarOSComponent } from '../cancelar-os/cancelar-os.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Colaborador } from 'src/app/models/Colaborador';
import { ColaboradorService } from 'src/app/services/colaborador.service';



@Component({
  selector: 'app-os-create',
  templateUrl: './os-create.component.html',
  styleUrls: ['./os-create.component.css']
})

export class OsCreateComponent implements OnInit {
  
  colaboradores:Colaborador[] = [];
  osCreateForm:FormGroup;
  doc:string = '';
  nomeCliente:string = '';
  cttPrincipalCliente:string = '';
  enderecoCliente:string = '';
  colaboradorOs: string = '';
  
  constructor(
    private clienteService:ClienteService,
    private dialog: MatDialog,
    private fb:FormBuilder,
    private colaboradorService: ColaboradorService
  ) { }

  ngOnInit(): void {
    
    this.osCreateForm = this.fb.group({
      empresa:[],
      cliente:[],
      colaborador:[],
      aparelhos:[],
      descricaoModelo:[],
      checkList:[],
      reclamacaoCliente:[],
      laudoChamado:[],
      statusOS:[]
    });

    this.dropdownColaborador();
  }


  openDialogCancelarOs(){
    const cancelarOs = this.dialog.open(CancelarOSComponent);
    
    cancelarOs.afterClosed().subscribe(result => {
      if(result == 'closeDAta'){
        this.apagarDadosCliente();

      }
    })
  
  }

  openDialog(){
  const dialogRef = this.dialog.open(ClienteCreateOsComponent,{
      data: {documento: this.doc},disableClose:true
    }); 
    
    dialogRef.afterClosed().subscribe(result => {
      if(result == 'closeData'){
        this.apagarDadosCliente();
        
      }

      this.buscarClientePorDoc();

    })
    
  }

  apagarDadosCliente(){
    this.doc = '';
    this.nomeCliente = '';
    this.enderecoCliente = '';
  }

  cancelarCriacaoOs(){
    this.apagarDadosCliente();

  }

  buscarClientePorDoc(){
  debugger
      
      this.clienteService.encontrarClientePorDocumento(this.doc).subscribe(response =>{
      
      if(response.id != null){
        this.nomeCliente = response.nome,
        this.cttPrincipalCliente = response.cel1,
        this.enderecoCliente = response.logradouro + ',' + response.numero +'.'
      }
      
      
    }, error => {
      if(this.doc != null && this.doc != '')
      this.openDialog();    
      return
    }
  
  
  );

  }

  dropdownColaborador(){
    debugger;
    this.colaboradorService.findAll().subscribe(
      (response) => {
        this.colaboradores = response;     
    }),
    (error) => {
      console.error("Não foi possível carregar os colaboradores.")
    }
  };


}
