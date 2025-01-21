import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Cliente } from 'src/app/models/Cliente';
import {MatPaginator} from '@angular/material/paginator';
import { ClienteService } from 'src/app/services/cliente.service';
import { ClientesDeleteComponent } from '../clientes-delete/clientes-delete.component';
import { ClientesUpdateComponent } from '../clientes-update/clientes-update.component';
import { MatDialog } from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-cliente-list',
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes-list.component.css']
})

export class ClientesListComponent implements OnInit {
  ELEMENT_DATA: Cliente[]=[];
  dataSource = new MatTableDataSource<Cliente>(this.ELEMENT_DATA);
  displayedColumns: string[] = ['id','nome','documento','email','cel1','acoes'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  
  
  constructor(
    private cliService:ClienteService,
    private dialog: MatDialog
  ) { }

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

  openDialog(event: Event, id: string): void {
    
    const dialogRef = this.dialog.open(ClientesUpdateComponent, {   
      data: { id: id }
    });
  }

  openDelDialog(event: Event, id: string): void {
    const dialog = this.dialog.open(ClientesDeleteComponent, {
      data: {id:id},
      width: '250px',
  });
}


}
