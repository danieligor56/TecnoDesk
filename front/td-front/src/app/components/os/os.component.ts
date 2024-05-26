import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup } from '@angular/forms';


@Component({
  selector: 'app-os',
  templateUrl: './os.component.html',
  styleUrls: ['./os.component.css']
})
export class OSComponent implements OnInit {

  clienteCreateForm:FormGroup;

 

    constructor(private fb:FormBuilder) { }
   

    ngOnInit(): void {
      this.clienteCreateForm = this.fb.group({
        nome:[],
        

      })
    }
  
    
  

}
