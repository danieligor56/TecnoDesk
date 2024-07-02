import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Colaborador } from 'src/app/models/Colaborador';
import { ColaboradorService } from 'src/app/services/colaborador.service';

@Component({
  selector: 'app-colaborador-list',
  templateUrl: './colaborador-list.component.html',
  styleUrls: ['./colaborador-list.component.css']
})
export class ColaboradorListComponent implements OnInit {

  ELEMENT_DATA: Colaborador[] = []
  
  displayedColumns: string[] = ['id', 'name', 'weight', 'symbol','email','acoes'];
  dataSource = new MatTableDataSource<Colaborador>(this.ELEMENT_DATA);
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  
  constructor(
    private colabService: ColaboradorService

  ) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll(){
    this.colabService.findAll().subscribe(resposta => {
      this.ELEMENT_DATA= resposta;
      this.dataSource = new MatTableDataSource<Colaborador>(resposta);
      this.dataSource.paginator = this.paginator;
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

};


