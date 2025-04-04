import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ClienteService } from 'src/app/services/cliente.service';
import { ClienteCreateComponent } from '../../clientes/cliente-create/cliente-create.component';
import { ClienteCreateOsComponent } from '../cliente-create-os/cliente-create-os.component';
import { CancelarOSComponent } from '../cancelar-os/cancelar-os.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Colaborador } from 'src/app/models/Colaborador';
import { ColaboradorService } from 'src/app/services/colaborador.service';
import { Os_entrada } from 'src/app/models/Os-entrada';
import { OsService } from 'src/app/services/os.service';
import { Toast, ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/Cliente';
import { OsCreateSucssesComponent } from './os-create-sucsses/os-create-sucsses.component';
import { Router } from '@angular/router';
import { PdfService } from 'src/app/services/pdf.service';

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
  clientes:number = 0;
  colaboradorOs:number = 0;
  
   
  constructor(
    private clienteService:ClienteService,
    private dialog: MatDialog,
    private fb:FormBuilder,
    private colaboradorService: ColaboradorService,
    private os:OsService,
    private toast:ToastrService,
    private router:Router,
    private pdfService:PdfService
  ) { }

  ngOnInit(): void {
    
    this.osCreateForm = this.fb.group({
      
      cliente:{id:0},
      colaborador:{id:this.colaboradorOs},
      aparelhos:[],
      descricaoModelo:[],
      checkList:[],
      reclamacaoCliente:[],
      initTest:[],
      statusOS:[0],
      prioridadeOS:[],
      marcaAparelho:[],
      snAparelho:[]
    
    });

    this.dropdownColaborador();
    console.log(this.colaboradorOs); // Verifique se está recebendo o valor corretamente
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
    this.cttPrincipalCliente = '';
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
        this.enderecoCliente = response.logradouro + ',' + response.numero +'.',
        
        this.osCreateForm.patchValue({
          cliente: {id: response.id}
        })
       
        
        
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
        this.colaboradores = response
    }),

    (error) => {
      console.error("Não foi possível carregar os colaboradores.")
    }

  };

  onColaboradorChange(colaboradorId: number) {
    this.osCreateForm.patchValue({
      colaborador: { id: colaboradorId }
    });
  }

  apagarOs(){
    this.osCreateForm.reset();
    this.apagarDadosCliente();

  }

  openDialogSuccess(response:Os_entrada){
    debugger
    const dialogRef = this.dialog.open(OsCreateSucssesComponent,{
      data: {form: this.osCreateForm }
    });

    dialogRef.afterClosed().subscribe(result => {
   
      switch(result){
       
        case 'novaOs': this.apagarOs(); 
          break;
        
        case 'gerarPdf':this.pdfService.gerarPdfOsEntrada(response).subscribe((os:Blob) => {
          const url = window.URL.createObjectURL(os);
          const link = document.createElement('a');
          link.href = url
          window.open(url,'_blank')
          link.download = 'Ordem de serviço Nº: '+response.numOs+'.pdf'
          link.click(); 
        }),
        
        this.apagarOs();
          break;
        
        case 'menu':this.router.navigate(['home']);
          break;
        
        
      }


    })
  }

  createOsEntrada(){
    debugger;
    this.os.createOsEntrada(this.osCreateForm.value).subscribe(
      (response) => {      
            
            this.openDialogSuccess(response)
          
           },         
      (error) => {
        this.toast.error("Falha na criação da ordem de serviço, contate o suporte");
       
        
      }
    ); 
  }


}
