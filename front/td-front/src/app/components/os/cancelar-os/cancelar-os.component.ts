import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { OsCreateComponent } from '../os-create/os-create.component';

@Component({
  selector: 'app-cancelar-os',
  templateUrl: './cancelar-os.component.html',
  styleUrls: ['./cancelar-os.component.css']
})
export class CancelarOSComponent implements OnInit {

  constructor(private dialog:MatDialog,private router:Router) { }

  ngOnInit(): void {
  }

  cancelarOs(){
    this.dialog.closeAll();
  }

  apagarOs(){
    location.reload();
  }



}
