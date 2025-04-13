import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-item-service-create-avulso',
  templateUrl: './item-service-create-avulso.component.html',
  styleUrls: ['./item-service-create-avulso.component.css']
})
export class ItemServiceCreateAvulsoComponent implements OnInit {
isService:boolean = false;

  constructor(
    private dialogRef: MatDialogRef<ItemServiceCreateAvulsoComponent>
  ) { }

  ngOnInit(): void {
  }

  closeDialog(){
    this.dialogRef.close();
  }
  

}
