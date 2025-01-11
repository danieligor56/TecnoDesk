import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Os_entrada } from 'src/app/models/Os-entrada';
import { OsService } from 'src/app/services/os.service';


@Component({
  selector: 'app-os-list',
  templateUrl: './os-list.component.html',
  styleUrls: ['./os-list.component.css']
})
export class OsListComponent implements OnInit {
  ELEMENT_DATA: Os_entrada[]=[];  
  dataSource = new MatTableDataSource<Os_entrada>(this.ELEMENT_DATA);
  displayedColumns: string[] = ['numOs','cliente','colaborador','statusOS','prioridadeOS'];

  
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private osService:OsService,  
  ) { }

  ngOnInit(): void {
    this.findAllOS();
  }

  findAllOS(){
    debugger
    this.osService.findAllOs().subscribe( 
      response => {
        this.ELEMENT_DATA = response;
        this.dataSource = new MatTableDataSource<Os_entrada>(response);
        this.dataSource.paginator = this.paginator;
      }
    )
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
