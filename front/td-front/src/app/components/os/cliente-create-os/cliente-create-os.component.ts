import { Component, Inject, OnInit } from '@angular/core';
import { AbstractControlOptions, Form, FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CepService } from 'src/app/services/autoCep.service';
import { ClienteService } from 'src/app/services/cliente.service';
import { OsCreateComponent } from '../os-create/os-create.component';
import { ClienteCreateComponent } from '../../clientes/cliente-create/cliente-create.component';

@Component({
  selector: 'app-cliente-create-os',
  templateUrl: './cliente-create-os.component.html',
  styleUrls: ['./cliente-create-os.component.css']
})
export class ClienteCreateOsComponent implements OnInit {
  clienteCreateForm: FormGroup;
  validadorDeSenhas:boolean = false;
  form2:boolean = false;
  form1:boolean = true;
  
  constructor(
    private fb: FormBuilder,
    private servCep: CepService,
    private toast: ToastrService,
    private router:Router,
    private clienteService:ClienteService,
    private dialog:MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<ClienteCreateComponent>

  ) { }
 

  ngOnInit(): void {

    this.clienteCreateForm = this.fb.group({
      empresa:[],
      nome: [
        null,
        Validators.minLength(4)],
      documento: [null,Validators.minLength(11)],
      email: [null,Validators.email],
      cel1: [null,Validators.minLength(11)],
      cel2: [],
      logradouro: [null],
      cep: [''],
      cidade: [null],
      estado: [null],
      bairro: [null],
      numero: [null],
      obs: [null],
      atvReg:[true],

    }),

    this.clienteCreateForm.get("documento").setValue(this.data.documento)
  }

  autoCep(cep:String){
    this.servCep.buscaCep(cep).subscribe(
      (data)=>{
        this.clienteCreateForm.patchValue({

          logradouro: data.logradouro,
          cidade: data.localidade,
          estado: data.uf,
          bairro: data.bairro
       
        })
      }
    )
  }

  cadastrarEndereco(){
    debugger;
    this.form1 = false;
     this.form2 = true;
    }
  
    cadastraInfoPessoal(){
      this.form2=false;
      this.form1=true;
    }

    closeDialog(){
      this.dialogRef.close('closeData')
    }

    

  validaCampos():boolean{

    const nomeValid = this.clienteCreateForm.get('nome').valid;
    const documentoValid = this.clienteCreateForm.get('documento').valid;
    const emailValid = this.clienteCreateForm.get('email').valid;
    const cel1Valid = this.clienteCreateForm.get('cel1').valid;
  
      if(nomeValid && documentoValid && emailValid && cel1Valid ){
        
        return true
        
        }
      else{
        
          return  false
      }


  }

  create(): void {      
    this.clienteCreateForm.get('')
    debugger;         
    this.clienteService.create(this.clienteCreateForm.value)
    .subscribe(resposta => {
    this.dialog.closeAll(); 
          
           })            
  }

}
