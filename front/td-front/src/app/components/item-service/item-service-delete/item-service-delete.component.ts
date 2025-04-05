import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ItemServiceService } from 'src/app/services/item-service.service';

@Component({
  selector: 'app-item-service-delete',
  templateUrl: './item-service-delete.component.html',
  styleUrls: ['./item-service-delete.component.css']
})
export class ItemServiceDeleteComponent implements OnInit {

  constructor(
     
     private itemServico:ItemServiceService,
     private toast:ToastrService,
     private dialogRef: MatDialogRef<ItemServiceDeleteComponent>,
            @Inject(MAT_DIALOG_DATA) public data: { id: string }
  ) { }

  ngOnInit(): void {
  }
  
  closeDialog(){
    this.dialogRef.close();
  }

  deletarServico(){
    debugger;
    const id = Number(this.data.id)
    this.itemServico.deletarServico(id).subscribe( response =>{
      if(response)
        
          this.toast.success("Serviço exluído com sucesso")
          this.dialogRef.close(true);
    })
      error: (error) => {
        this.toast.error('Falha ai excluir o registro'+ error)
      }

  }



}
