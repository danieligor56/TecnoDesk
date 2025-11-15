import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Colaborador } from 'src/app/models/Colaborador';
import { ColaboradorService } from 'src/app/services/colaborador.service';
import { ColaboradorUpdateComponent } from '../colaborador-update/colaborador-update.component';
import { ColaboradorDeleteComponent } from '../colaborador-delete/colaborador-delete.component';
import { ColaboradorCreateComponent } from '../colaborador-create/colaborador-create.component';

@Component({
  selector: 'app-colaborador-list',
  templateUrl: './colaborador-list.component.html',
  styleUrls: ['./colaborador-list.component.css']
})
export class ColaboradorListComponent implements OnInit {

  ELEMENT_DATA: Colaborador[] = []
  
  displayedColumns: string[] = ['id', 'name', 'weight','email','acoes'];
  dataSource = new MatTableDataSource<Colaborador>(this.ELEMENT_DATA);
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  
  constructor(
    private colabService: ColaboradorService,
    private dialog:MatDialog

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


  openDialog(event: Event, id: string): void {
    
    const dialogRef = this.dialog.open(ColaboradorUpdateComponent, {   
      data: { id: id }
    });

     dialogRef.afterClosed().subscribe( result => {
        if(result)
          this.findAll()
      })

  }

  openDelDialog(event: Event, id: string): void {
    
    const dialog = this.dialog.open(ColaboradorDeleteComponent, {
      data: {id:id},
      width: '420px',
  });

    dialog.afterClosed().subscribe( result => {
        if(result)
          this.findAll()
      })

}

 criarColaboradorDialog(){
    const dialogRef = this.dialog.open(ColaboradorCreateComponent);

    dialogRef.afterClosed().subscribe( result =>{
        if(result)
          this.findAll()
      })


  }





}


