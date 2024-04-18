import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Colaborador } from 'src/app/models/Colaborador';
import { ColaboradorService } from 'src/app/services/colaborador.service';

@Component({
  selector: 'app-colaborador-list',
  templateUrl: './colaborador-list.component.html',
  styleUrls: ['./colaborador-list.component.css']
})
export class ColaboradorListComponent implements AfterViewInit {

  ELEMENT_DATA: Colaborador[] = []
  
  displayedColumns: string[] = ['id', 'name', 'weight', 'symbol','acoes','email'];
  dataSource = new MatTableDataSource<Colaborador>(this.ELEMENT_DATA);

  constructor(
    private colabService: ColaboradorService

  ) { }

  ngOnInit(): void {
    this.findAll();
  }
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  findAll(){
    this.colabService.findAll().subscribe(resposta => {
      this.ELEMENT_DATA= resposta;
      this.dataSource = new MatTableDataSource<Colaborador>(resposta);
    })
  }

};


