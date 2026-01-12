import { Component, Inject, OnInit } from '@angular/core';
import { SelectionChange } from '@angular/cdk/collections';
import { AbstractControlOptions, Form, FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Toast, ToastrService } from 'ngx-toastr';
import { Ocupacao } from 'src/app/enuns/Ocupacao';
import { AuthService } from 'src/app/services/auth.service';
import { UtilsService } from 'src/app/services/UtilsService.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-clientes-update',
  templateUrl: './clientes-update.component.html',
  styleUrls: ['./clientes-update.component.css']
})
export class ClientesUpdateComponent implements OnInit {
  clienteCreateForm: FormGroup;
  validadorDeSenhas:boolean = false;
  form2:boolean = false;
  form1:boolean = true;   

  constructor(
    private fb: FormBuilder,
    private servCep: UtilsService,
    private toast: ToastrService,
    private router:Router,
    private clienteService:ClienteService,

    public dialogRef: MatDialogRef<ClientesUpdateComponent>,
     @Inject(MAT_DIALOG_DATA) public data: { id: string }

  ) { }

  ngOnInit(): void {

    this.clienteCreateForm = this.fb.group({
      id:[this.data.id],
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
    this.getById();
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
          this.clienteCreateForm.patchValue({ 
            logradouro: data.logradouro,
            cidade: data.localidade,
            estado: data.uf,
            bairro: data.bairro
          })
        }
      )
    }

    validaCampos():boolean{

      const nomeValid = this.clienteCreateForm.get('nome').valid;
      const documentoValid = this.clienteCreateForm.get('documento').valid;
      // const emailValid = this.clienteCreateForm.get('email').valid;
      const cel1Valid = this.clienteCreateForm.get('cel1').valid;
    
        if(nomeValid && documentoValid && cel1Valid ){
          
          return true
          
          }
        else{
          
            return  false
        }
  
  
    }

    getById() {
      this.clienteService.findByID(this.clienteCreateForm.get('id').value).subscribe(resposta => {
        Object.keys(resposta).forEach(key => {
          if (this.clienteCreateForm.get(key)) {
            this.clienteCreateForm.get(key).setValue(resposta[key]);
          }
        });
          
      });
    }

    updateColaborador(): void{

      const id = this.clienteCreateForm.get('id').value;
      const clienteData = this.clienteCreateForm.value;
  
      this.clienteService.alterarCliente(id,this.clienteCreateForm.value)
      .subscribe(resposta =>
    {
      location.reload();
        this.toast.success('Alteração realizado com sucesso');
            this.dialogRef.close(); 
      })
    }


}
