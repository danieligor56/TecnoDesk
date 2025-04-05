import { Component, OnInit, AfterViewInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import { ItemService } from 'src/app/models/ItemService';
import { ItemServiceService } from 'src/app/services/item-service.service';
import { ItemServiceCreateComponent } from './item-service-create/item-service-create.component';




@Component({
  selector: 'app-item-service',
  templateUrl: './item-service.component.html',
  styleUrls: ['./item-service.component.css']
})
export class ItemServiceComponent implements OnInit {
  servico: ItemService [] = [];
  displayedColumns: string[] = ['id', 'progress','fruit','name' ];
  dataSource = new MatTableDataSource<ItemService>(this.servico);
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private itemService:ItemServiceService,
    private dialog:MatDialog
  ) {}
  
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

  openDialog(): void {
      
      const dialogRef = this.dialog.open(ItemServiceCreateComponent);
      
      dialogRef.afterClosed().subscribe( result=>{
        if(result){
          this.encontrarServicos();
        }
      })
    }

}






