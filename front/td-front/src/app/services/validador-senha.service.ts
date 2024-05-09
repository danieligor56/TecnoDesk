import { Injectable } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidadorSenhaService {

  constructor() { }

  static validaSenha(senha:string){
    const validador = (formControl:FormControl)=> {
      if(senha == null){
        throw new Error('É necessário informar a senha');
      }

      const campo = (<FormGroup>formControl.root).get(senha)

    }
  }

}
