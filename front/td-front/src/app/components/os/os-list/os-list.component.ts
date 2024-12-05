import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';

@Component({
  selector: 'app-os-list',
  templateUrl: './os-list.component.html',
  styleUrls: ['./os-list.component.css']
})
export class OsListComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
