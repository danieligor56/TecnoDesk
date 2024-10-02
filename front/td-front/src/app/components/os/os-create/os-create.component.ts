import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ClienteService } from 'src/app/services/cliente.service';
import { ClienteCreateComponent } from '../../clientes/cliente-create/cliente-create.component';
import { ClienteCreateOsComponent } from '../cliente-create-os/cliente-create-os.component';


@Component({
  selector: 'app-os-create',
  templateUrl: './os-create.component.html',
  styleUrls: ['./os-create.component.css']
})
export class OsCreateComponent implements OnInit {
  doc:string = '';
  nomeCliente:string = '';
  cttPrincipalCliente:string = '';
  enderecoCliente:string = '';
  

  
  constructor(private clienteService:ClienteService,private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openDialog(){
  const dialogRef = this.dialog.open(ClienteCreateOsComponent,{
      data: {documento: this.doc}
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


}
