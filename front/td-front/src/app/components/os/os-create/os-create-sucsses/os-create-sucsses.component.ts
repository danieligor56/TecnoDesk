import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Route, Router } from '@angular/router';
import { HomeComponent } from 'src/app/components/home/home.component';
import { OsCreateComponent } from '../os-create.component';

@Component({
  selector: 'app-os-create-sucsses',
  templateUrl: './os-create-sucsses.component.html',
  styleUrls: ['./os-create-sucsses.component.css']
})
export class OsCreateSucssesComponent implements OnInit {

  constructor( 
    private dialog:MatDialog,
    private router:Router,
    public dialogRef: MatDialogRef<OsCreateSucssesComponent>
  ) { }

  ngOnInit(): void {
  }

  closeDialog(){   
    this.dialogRef.close('novaOs')
  }

  generatePdf(){
    this.dialogRef.close('gerarPdf')
  }

  goToMenu(){
    this.dialogRef.close('menu')
  }

}
