import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Route, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ItemService } from 'src/app/models/ItemService';
import { ItemServiceService } from 'src/app/services/item-service.service';

@Component({
  selector: 'app-item-service-create',
  templateUrl: './item-service-create.component.html',
  styleUrls: ['./item-service-create.component.css']
})
export class ItemServiceCreateComponent implements OnInit {
  sevicoItemCreateForm:FormGroup;
  disable:boolean = false;
  
  constructor(
    private dialogRef: MatDialogRef<ItemServiceCreateComponent>,
    private fb: FormBuilder,
    private itemServicoService:ItemServiceService,
    private toast:ToastrService,
    private router:Router,
    
  ) { }

  ngOnInit(): void {
    this.sevicoItemCreateForm = this.fb.group({
        empresa:[],
        nomeServico:[null,Validators.required],
        descricaoServico:[],
        valorServicoHora:[null, [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
        valorServicoUnidade:[]
    })
  }

  closeDialog(){
    this.dialogRef.close();
  }

  validarNome(){
    debugger;
    if(this.sevicoItemCreateForm.get('nomeServico').valid){
      this.disable = true;
    }
  }

  createItemServico(){
    this.itemServicoService.create(this.sevicoItemCreateForm.value).subscribe({
       next: (response) => {
          this.toast.success("Cadastro realizado  com sucesso ! ");
          this.dialogRef.close(true);        
           
       },
       error: (error) => {
          this.toast.error("Erro durante a criação do serviço, contate o suporte."); 
       }
    });
  }
    
    
  

}
