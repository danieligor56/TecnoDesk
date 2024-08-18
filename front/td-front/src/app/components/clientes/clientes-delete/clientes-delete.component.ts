import { Component, OnInit, Inject  } from "@angular/core";
import { MatDialog } from '@angular/material/dialog';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Route, Router } from "@angular/router";
import { Toast, ToastrService } from "ngx-toastr";
import { ClienteService } from "src/app/services/cliente.service";

@Component({
  selector: 'app-clientes-delete',
  templateUrl: './clientes-delete.component.html',
  styleUrls: ['./clientes-delete.component.css']
})
export class ClientesDeleteComponent implements OnInit {

  constructor(
    private clienteService:ClienteService,
    private dialog:MatDialog,
    private route:Router,
    private toast:ToastrService,

     public dialogRef: MatDialogRef<ClientesDeleteComponent>,
     @Inject(MAT_DIALOG_DATA) public data: { id: bigint }
  ) { }

  ngOnInit(): void {
  }

  deletarCliente(){
    
    this.clienteService.deletarCliente(this.data.id)
    .subscribe (resposta => {
        location.reload();
        this.toast.success("Registro apagado com sucesso")       
        
        
      }) 
    }

}
