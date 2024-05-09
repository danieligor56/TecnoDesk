import { SelectionChange } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { AbstractControlOptions, Form, FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Toast, ToastrService } from 'ngx-toastr';
import { CepService } from 'src/app/services/autoCep.service';
import Validation from 'src/app/validators/validadorSenha';



@Component({
  selector: 'app-colaborador-create',
  templateUrl: './colaborador-create.component.html',
  styleUrls: ['./colaborador-create.component.css']
})

export class ColaboradorCreateComponent implements OnInit {
 

  colaboradorCreateForm: FormGroup;
  
  //
  validadorDeSenhas:boolean = false;
  isUsuario:boolean = false;
  form2:boolean = false;
  form1:boolean = true;

  constructor(private fb: FormBuilder,private servCep: CepService, private toast: ToastrService) 
  {
    
 
 
}
   ngOnInit(): void {
    this.colaboradorCreateForm = this.fb.group({
      nome: [
        null,
        Validators.minLength(4)],
      documento: [null,Validators.minLength(11)],
      email: [null,Validators.email],
      cel1: [null,Validators.minLength(11)],
      logradouro: [null],
      cep: [''],
      cidade: [null],
      estado: [null],
      bairro: [null],
      numero: [null],
      senha:  [null,Validators.required],
      confirmaSenha: [
        null,
        Validators.required]}
      ,{
        validators: [Validation.match('senha','confirmaSenha')]
    }
  )}

  cadastrarEndereco(){
  this.form1 = false;
   this.form2 = true;
  }

  cadastraInfoPessoal(){
    this.form2=false;
    this.form1=true;
  }

  autoCep(cep:String){
    this.servCep.buscaCep(cep).subscribe(
      (data)=>{
        this.colaboradorCreateForm.patchValue({

          logradouro: data.logradouro,
          cidade: data.localidade,
          estado: data.uf,
          bairro: data.bairro
        })
      }
    )
  }

  returnToSetSenha(){
    if(!this.isUsuario && this.form2  && !this.form1){
      this.cadastraInfoPessoal();
    }
  }

  validaCampos():boolean{

    const nomeValid = this.colaboradorCreateForm.get('nome').valid;
    const documentoValid = this.colaboradorCreateForm.get('documento').valid;
    const emailValid = this.colaboradorCreateForm.get('email').valid;
    const cel1Valid = this.colaboradorCreateForm.get('cel1').valid;
    const senhaValid = this.colaboradorCreateForm.get('senha').valid;
    const confirmaSenhaValid = this.colaboradorCreateForm.get('confirmaSenha').valid;
    
    

    if(this.isUsuario == true){
      if(nomeValid && documentoValid && emailValid && cel1Valid && confirmaSenhaValid ){
        return true
        }
      }else{
        if(nomeValid && documentoValid && emailValid && cel1Valid)
          return  true
      }

      return false;

  }


}
