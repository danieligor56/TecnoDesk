import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Colaborador } from 'src/app/models/Colaborador';

@Component({
  selector: 'app-colaborador-list',
  templateUrl: './colaborador-list.component.html',
  styleUrls: ['./colaborador-list.component.css']
})
export class ColaboradorListComponent implements AfterViewInit {

  ELEMENT_DATA: Colaborador[] = [
    {
      id:1,
	    nome:'Daniel Igor',
      documento:'036.853.133-39',
      ocupacao:'Técnico',
	    email:'daniel.vale@gmail.com',
    	tel1:85985727393,
	    cel1:85985727393,
	    estado:'CE',
	    cidade:'Fortaleza',
	    logradouro:'Henrrique Autran 46',	
	    numero:46,
	    obs:'Utma casa da vila',	
      atvReg:true
      
    }
  ]
  
  displayedColumns: string[] = ['id', 'name', 'weight', 'symbol','acoes','email'];
  dataSource = new MatTableDataSource<Colaborador>(this.ELEMENT_DATA);

  constructor() { }

  ngOnInit(): void {
  }


  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

};


