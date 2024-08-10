
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Cliente } from 'src/app/models/Cliente';
import {MatPaginator} from '@angular/material/paginator';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-clientes-list',
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes-list.component.css']
})
export class ClientesListComponent implements OnInit {
  ELEMENT_DATA: Cliente[]=[];
  dataSource = new MatTableDataSource<Cliente>(this.ELEMENT_DATA);
  displayedColumns: string[] = ['id','nome','documento','email','cel1'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  
  constructor(private cliService:ClienteService) { }

  ngOnInit(): void {
    this.findAllcli();
  }

  findAllcli(){
    this.cliService.findAll()
    .subscribe(response => {
      this.ELEMENT_DATA = response;
      this.dataSource = new MatTableDataSource<Cliente>(response);
      this.dataSource.paginator = this.paginator;
    
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


}
