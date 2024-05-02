import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Creds } from 'src/app/models/creds';

@Component({
  selector: 'app-colaborador-create',
  templateUrl: './colaborador-create.component.html',
  styleUrls: ['./colaborador-create.component.css']
})
export class ColaboradorCreateComponent implements OnInit {

  nome: FormControl = new FormControl(null,Validators.minLength(4))
  documento: FormControl = new FormControl(null,Validators.minLength(11))
  email: FormControl = new FormControl(null,Validators.email)
  cel1:FormControl = new FormControl(null,Validators.minLength(11))
  
  isUsuario:boolean = false;



  constructor() { }

  ngOnInit(): void {
  }

  validaCampos(): boolean {    
    return this.nome.valid && this.documento.valid 
    && this.email.valid && this.cel1.valid 
  }


}
