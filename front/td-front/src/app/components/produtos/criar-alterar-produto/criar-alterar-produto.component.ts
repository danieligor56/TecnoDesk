import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { StepperOrientation } from '@angular/material/stepper';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';


@Component({
  selector: 'app-criar-alterar-produto',
  templateUrl: './criar-alterar-produto.component.html',
  styleUrls: ['./criar-alterar-produto.component.css']
})
export class CriarAlterarProdutoComponent implements OnInit {
 
  checked = this.data.eNovoProduto;
  disabled = false;
  selected = 'option2';
  btn1valid: boolean = false;

  firstFormGroup = this._formBuilder.group({

    nome: ['', Validators.required],
    descricao: ['',Validators.required],
    marca: ['',Validators.required],
    codigo_barras: ['',Validators.required],
    produtoAtivo: ['',Validators.required]
  
  });

  secondFormGroup = this._formBuilder.group({
    preco: ['', Validators.required],
    precoCusto: ['', Validators.required]
  });

  thirdFormGroup = this._formBuilder.group({

    unidadeMedida: ['', Validators.required],
    quantidadeEstoque: ['', Validators.required]
    
  });
  stepperOrientation: Observable<StepperOrientation>;

  constructor(private _formBuilder: FormBuilder,
     breakpointObserver: BreakpointObserver,
     public dialogRef: MatDialogRef<CriarAlterarProdutoComponent>,
         @Inject(MAT_DIALOG_DATA) public data: { eNovoProduto: boolean }
        ) {
    this.stepperOrientation = breakpointObserver.observe('(min-width: 800px)')
      .pipe(map(({matches}) => matches ? 'horizontal' : 'vertical'));

      

  }

  validarBtn1(){
   debugger;
    if(this.firstFormGroup.get('nome')?.valid && this.firstFormGroup.get('descricao')?.valid ){
      this.btn1valid = true;
    }
          
  }


  


  ngOnInit(): void {
  }

 

}
