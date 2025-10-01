import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

import { MatDialog } from '@angular/material/dialog';
import { StepperOrientation } from '@angular/material/stepper';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';


@Component({
  selector: 'app-criar-alterar-produto',
  templateUrl: './criar-alterar-produto.component.html',
  styleUrls: ['./criar-alterar-produto.component.css']
})
export class CriarAlterarProdutoComponent implements OnInit {
  checked = false;
  disabled = false;
   selected = 'option2';
 

  
   emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  

  firstFormGroup = this._formBuilder.group({
    firstCtrl: ['', Validators.required]
  });
  secondFormGroup = this._formBuilder.group({
    secondCtrl: ['', Validators.required]
  });
  thirdFormGroup = this._formBuilder.group({
    thirdCtrl: ['', Validators.required]
  });
  stepperOrientation: Observable<StepperOrientation>;

  constructor(private _formBuilder: FormBuilder, breakpointObserver: BreakpointObserver) {
    this.stepperOrientation = breakpointObserver.observe('(min-width: 800px)')
      .pipe(map(({matches}) => matches ? 'horizontal' : 'vertical'));
  }



  ngOnInit(): void {
  }

 

}
