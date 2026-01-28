import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Cliente } from 'src/app/models/Cliente';
import { MatPaginator } from '@angular/material/paginator';
import { ClienteService } from 'src/app/services/cliente.service';
import { ClientesDeleteComponent } from '../clientes-delete/clientes-delete.component';
import { ClientesUpdateComponent } from '../clientes-update/clientes-update.component';
import { MatDialog } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { ClienteCreateComponent } from '../cliente-create/cliente-create.component';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-cliente-list',
  templateUrl: './clientes-list.component.html',
  styleUrls: ['./clientes-list.component.css']
})

export class ClientesListComponent implements OnInit {
  ELEMENT_DATA: Cliente[] = [];
  dataSource = new MatTableDataSource<Cliente>(this.ELEMENT_DATA);
  displayedColumns: string[] = ['sequencial', 'nome', 'documento', 'email', 'cel1', 'acoes'];
  isLoading = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;


  constructor(
    private cliService: ClienteService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.findAllcli();
  }

  findAllcli() {
    this.isLoading = true;
    this.cliService.findAll()
      .subscribe(response => {
        this.ELEMENT_DATA = response;
        this.dataSource = new MatTableDataSource<Cliente>(response);
        this.dataSource.paginator = this.paginator;
        this.dataSource.filterPredicate = (data: Cliente, filter: string) => {
          const filterValue = filter.trim().toLowerCase();
          return data.sequencial?.toString().includes(filterValue) ||
            data.nome?.toLowerCase().includes(filterValue) ||
            data.documento?.toLowerCase().includes(filterValue) ||
            data.email?.toLowerCase().includes(filterValue) ||
            data.cel1?.toLowerCase().includes(filterValue);
        };
        this.isLoading = false;
      }, error => {
        this.isLoading = false;
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
      data: { id: id },
      width: '420px',
    });
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(ClienteCreateComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result)
        this.findAllcli()
    })
  }

  abrirOsComCliente(id) {
    this.router.navigate(['/os/create', id])
  }


}
