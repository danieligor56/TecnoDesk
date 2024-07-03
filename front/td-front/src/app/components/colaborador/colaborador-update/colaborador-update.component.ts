
import { SelectionChange } from '@angular/cdk/collections';
import { Component, OnInit } from '@angular/core';
import { AbstractControlOptions, Form, FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Toast, ToastrService } from 'ngx-toastr';
import { Ocupacao } from 'src/app/enuns/Ocupacao';
import { AuthService } from 'src/app/services/auth.service';
import { CepService } from 'src/app/services/autoCep.service';
import { ColaboradorService } from 'src/app/services/colaborador.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import Validation from 'src/app/validators/validadorSenha';

@Component({
  selector: 'app-colaborador-update',
  templateUrl: './colaborador-update.component.html',
  styleUrls: ['./colaborador-update.component.css']
})
export class ColaboradorUpdateComponent implements OnInit {
  colaboradorCreateForm: FormGroup;
  validadorDeSenhas:boolean = false;
  isUsuario:boolean = false;
  form2:boolean = false;
  form1:boolean = true;
  
  listOcupacao:Ocupacao[] = [];
  isTecnico:boolean = false;
  isAtendente:boolean = false;
  isGestor: boolean = false;

  constructor(
     private fb: FormBuilder,
     private servCep: CepService,
     private toast: ToastrService,
     private colaboradorService:ColaboradorService,
     private router:Router,
     private service:AuthService,
     private usuarioService:UsuarioService,
     private route:ActivatedRoute
  ) { }

  ngOnInit(): void {

    this.colaboradorCreateForm = this.fb.group({
      id:[this.route.snapshot.paramMap.get('id')],
      nome: [
        null,
        Validators.minLength(4)],
      documento: [null,Validators.minLength(11)],
      ocupacao: [Validators.required,Validators.minLength(1)],
      email: [null,Validators.email],
      cel1: [null,Validators.minLength(11)],
      logradouro: [null],
      cep: [''],
      cidade: [null],
      estado: [null],
      bairro: [null],
      numero: [null],
      obs: [null],
      empresa: [],
      atvReg:[true],
      senha:  [null,Validators.required],
      confirmaSenha: [
        null,
        Validators.required]}
      ,{
        validators: [Validation.match('senha','confirmaSenha')]
    }
  );
    this.getById();
  }

  setOcupacao(){
    if(this.isTecnico === true){
      this.listOcupacao.push(Ocupacao.TECNICO);
    }
    if(this.isAtendente === true ){
      this.listOcupacao.push(Ocupacao.ATENDENTE);
   }
    if(this.isGestor === true){
      this.listOcupacao.push(Ocupacao.GESTOR);
    }

    this.colaboradorCreateForm.get('ocupacao').setValue(this.listOcupacao);
    
  }

getById() {
  this.colaboradorService.findByID(this.colaboradorCreateForm.get('id').value).subscribe(resposta => {
    Object.keys(resposta).forEach(key => {
      if (this.colaboradorCreateForm.get(key)) {
        this.colaboradorCreateForm.get(key).setValue(resposta[key]);
      }
    });
      resposta.ocupacao.forEach((ck:Ocupacao) => {
        if(ck === Ocupacao.TECNICO){
          this.isTecnico = true;  
        }

        if(ck === Ocupacao.ATENDENTE){
          this.isAtendente = true;
          }
          
          if(ck === Ocupacao.GESTOR){
            this.isGestor = true;
            }
        
      })
  });
}

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

validaCampos():boolean{

  const nomeValid = this.colaboradorCreateForm.get('nome').valid;
  const documentoValid = this.colaboradorCreateForm.get('documento').valid;
  const emailValid = this.colaboradorCreateForm.get('email').valid;
  const cel1Valid = this.colaboradorCreateForm.get('cel1').valid;
  
  if(nomeValid && documentoValid && emailValid && cel1Valid){
        return  true
    }

    return false;
}

}
