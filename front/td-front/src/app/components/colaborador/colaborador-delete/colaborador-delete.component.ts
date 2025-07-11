import { Component, OnInit, Inject  } from "@angular/core";
import { ColaboradorService } from "src/app/services/colaborador.service";
import { MatDialog } from '@angular/material/dialog';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Route, Router } from "@angular/router";
import { Toast, ToastrService } from "ngx-toastr";



@Component({
  selector: 'app-colaborador-delete',
  templateUrl: './colaborador-delete.component.html',
  styleUrls: ['./colaborador-delete.component.css']
})


export class ColaboradorDeleteComponent implements OnInit {

  constructor(
    private colabService:ColaboradorService,
    private dialog:MatDialog,
    private route:Router,
    private toast:ToastrService,

     public dialogRef: MatDialogRef<ColaboradorDeleteComponent>,
     @Inject(MAT_DIALOG_DATA) public data: { id: bigint }
  ) { }

  ngOnInit(): void {  
  }

  deletarColaborador(){
    this.colabService.deletarColaborador(this.data.id)
    .subscribe({
     next: resposta => {
        this.dialogRef.close(true);
        this.toast.success("Registro apagado com sucesso")
    },
    error: erro => {
        this.dialogRef.close();
        this.toast.error(erro.error.message)
      }
    })           
  }   
}

