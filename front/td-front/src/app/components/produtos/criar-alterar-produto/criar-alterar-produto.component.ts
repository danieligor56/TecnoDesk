import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { StepperOrientation } from '@angular/material/stepper';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Produtos } from 'src/app/models/Produtos';
import { ProdutosService } from 'src/app/services/produtos.service';


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
  btn2valid: boolean = false;
  activePass: boolean = false;

  firstFormGroup = this._formBuilder.group({

    nome: ['', Validators.required],
    descricao: ['',Validators.required],
    marca: ['',],
    codigo_barras: ['',],
    produtoAtivo: [this.checked,Validators.required]
  
  });

  secondFormGroup = this._formBuilder.group({
    preco: ['', Validators.required],
    precoCusto: ['']
  });

  thirdFormGroup = this._formBuilder.group({

    unidadeMedida: ['', Validators.required],
    quantidadeEstoque: ['', Validators.required]
    
  });
  stepperOrientation: Observable<StepperOrientation>;

  constructor(private _formBuilder: FormBuilder,
     breakpointObserver: BreakpointObserver,
     private produtosService: ProdutosService,
     public dialogRef: MatDialogRef<CriarAlterarProdutoComponent>,
         @Inject(MAT_DIALOG_DATA) public data: { eNovoProduto: boolean }
        ) {
    this.stepperOrientation = breakpointObserver.observe('(min-width: 800px)')
      .pipe(map(({matches}) => matches ? 'horizontal' : 'vertical'));

      

  }

  validarBtn1(){
    if(this.firstFormGroup.get('nome')?.valid){
      this.btn1valid = true;
      this.activePass = true
    } else {
       this.btn1valid = false;
       this.activePass = false;
    }
          
  }

  validarBtn2(){
        if(this.secondFormGroup.get('preco')?.valid){
          this.btn2valid = true;
            this.activePass = true
        } else {
          this.btn2valid = false;
            this.activePass = false;
        }
              
      }

  criarNovoProduto(){
debugger;
    if( this.firstFormGroup.get('nome')?.valid, 
        this.firstFormGroup.get('descricao')?.valid,
        this.secondFormGroup.get('preco')?.valid
       
      ){

      const novoProduto: Produtos = {
      nome: this.firstFormGroup.get('nome').value,
      descricao: this.firstFormGroup.get('descricao').value,
      marca: this.firstFormGroup.get('marca').value,
      codigo_barras: this.firstFormGroup.get('codigo_barras').value,
      produtoAtivo: this.firstFormGroup.get('produtoAtivo').value,
      preco: this.secondFormGroup.get('preco').value,
      precoCusto: this.secondFormGroup.get('precoCusto').value,
      unidadeMedida: this.thirdFormGroup.get('unidadeMedida').value,
      quantidadeEstoque: this.thirdFormGroup.get('quantidadeEstoque').value
        }
       
      this.produtosService.criarNovoProduto(novoProduto).subscribe( response => {
        if(response){

        }
      })  
        
    
      }

    


  }





  


  ngOnInit(): void {
  }

 

}
