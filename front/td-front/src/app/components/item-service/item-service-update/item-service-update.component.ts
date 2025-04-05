import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ItemServiceService } from 'src/app/services/item-service.service';

@Component({
  selector: 'app-item-service-update',
  templateUrl: './item-service-update.component.html',
  styleUrls: ['./item-service-update.component.css']
})
export class ItemServiceUpdateComponent implements OnInit {
  sevicoItemCreateForm:FormGroup;
  
   
  
  constructor(
    private fb: FormBuilder,
    private toast:ToastrService,
    private servicoService:ItemServiceService,
    public dialogRef: MatDialogRef<ItemServiceUpdateComponent>,
       @Inject(MAT_DIALOG_DATA) public data: { id: string }
  
  ) { }

  ngOnInit(): void {
    this.sevicoItemCreateForm = this.fb.group({
            id:[this.data.id],
            empresa:[],
            nomeServico:[null,Validators.required],
            descricaoServico:[],
            valorServicoHora:[null, [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
            valorServicoUnidade:[]
        })
        this.getById()
  }

  getById() {
    debugger;
    this.servicoService.encontrarPorId(this.sevicoItemCreateForm.get('id').value).subscribe(resposta => {
      Object.keys(resposta).forEach(key => {
        if (this.sevicoItemCreateForm.get(key)) {
          this.sevicoItemCreateForm.get(key).setValue(resposta[key]);
        }
      });
        
    });
  }

  closeDialog(){
    this.dialogRef.close();
  }

  alterarServico(){
    const id = Number(this.sevicoItemCreateForm.get('id')?.value);
      this.servicoService.alterarServico(id,this.sevicoItemCreateForm.value).subscribe( response =>{
        this.dialogRef.close(true); 
          this.toast.success('Serviço modificado com sucesso')
    })
      error: (error) => {
        this.toast.error('Não foi possível alterar o serviço'+ error)
      }
  }

}
