import { SelectionChange } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { AbstractControlOptions, Form, FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Toast, ToastrService } from 'ngx-toastr';
import { Ocupacao } from 'src/app/enuns/Ocupacao';
import { UtilsService } from 'src/app/services/UtilsService.service';
import { ClienteService } from 'src/app/services/cliente.service';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-cliente-create',
  templateUrl: './cliente-create.component.html',
  styleUrls: ['./cliente-create.component.css']
})

export class ClienteCreateComponent implements OnInit {

  clienteCreateForm: FormGroup;
  validadorDeSenhas:boolean = false;
  form2:boolean = false;
  form1:boolean = true;

  
  
  constructor(
    private dialogRef: MatDialogRef<ClienteCreateComponent>,
    private fb: FormBuilder,
    private servCep: UtilsService,
    private toast: ToastrService,
    private router:Router,
    private clienteService:ClienteService   
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

    })
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
    this.toast.success("Cadastro realizado  com sucesso ! ");
    this.router.navigate(['clientes']); 
    this.dialogRef.close(true);
          
    })            
  }

  closeDialog(){
    this.dialogRef.close();
  }


}
