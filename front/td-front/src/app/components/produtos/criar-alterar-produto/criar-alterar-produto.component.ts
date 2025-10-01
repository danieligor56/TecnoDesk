import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-criar-alterar-produto',
  templateUrl: './criar-alterar-produto.component.html',
  styleUrls: ['./criar-alterar-produto.component.css']
})
export class CriarAlterarProdutoComponent implements OnInit {
   emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);



  constructor(
    
  ) { }

  ngOnInit(): void {
  }

 

}
