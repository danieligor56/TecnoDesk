import { Component, OnInit, AfterViewInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { ItemService } from 'src/app/models/ItemService';
import { ItemServiceService } from 'src/app/services/item-service.service';



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
    private itemService:ItemServiceService) { 
      
    
  }
  
  ngOnInit(): void {
    this.encontrarServicos();
  }

  criarNovoServico(itemServico:ItemService){
      this.itemService.create(itemServico).subscribe(
        response =>{
          
        }
      )

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

}






