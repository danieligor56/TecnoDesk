import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ItemService } from 'src/app/models/ItemService';
import { ItemServiceService } from 'src/app/services/item-service.service';
import { ItemServiceCreateComponent } from '../item-service-create/item-service-create.component';




@Component({
  selector: 'app-item-servicel-minilist',
  templateUrl: './item-servicel-minilist.component.html',
  styleUrls: ['./item-servicel-minilist.component.css']
})
export class ItemServicelMinilistComponent implements OnInit {
servico: ItemService [] = [];
displayedColumns: string[] = ['id', 'progress','fruit','name','add'];
dataSource = new MatTableDataSource<ItemService>(this.servico);

@ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private itemService:ItemServiceService,
    private dialog:MatDialog,
    private dialogRef: MatDialogRef<ItemServicelMinilistComponent>
    
    
  ) { }

  ngOnInit(): void {
    this.encontrarServicos();
  }

  encontrarServicos(){
      this.itemService.listServico().subscribe(response => {
            this.servico = response;
            this.dataSource = new MatTableDataSource<ItemService>(response);
            this.dataSource.paginator = this.paginator;
            
          }
      )
      }

  applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    
          if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
          }
        }
  closeDialog(){
    this.dialogRef.close();
    }
  
    openCreatServiceDialog(){
    
      const dialogRef = this.dialog.open(ItemServiceCreateComponent);

    debugger;
    dialogRef.afterClosed().subscribe(response => {
      if(response)
        this.encontrarServicos();
    })
  }  

}
