import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-os',
  templateUrl: './os.component.html',
  styleUrls: ['./os.component.css']
})
export class OSComponent implements OnInit {

  disableSelect = new FormControl(false);

    constructor() { }

    ngOnInit(): void {
  }
  
    
  

}
