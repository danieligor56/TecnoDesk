import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Os_entrada } from 'src/app/models/Os-entrada';
import { OsService } from 'src/app/services/os.service';
import { PdfService } from 'src/app/services/pdf.service';
import { OsManagerComponent } from '../os-manager/os-manager.component';


@Component({
  selector: 'app-os-list',
  templateUrl: './os-list.component.html',
  styleUrls: ['./os-list.component.css']
})

export class OsListComponent implements OnInit {
  ELEMENT_DATA: Os_entrada[]=[];  
  dataSource = new MatTableDataSource<Os_entrada>(this.ELEMENT_DATA);
  displayedColumns: string[] = ['numOs','cliente','colaborador','statusOS','prioridadeOS','acoesOs'];
  filteredELEMENT_DATA: Os_entrada[] = [];
  osOne: Os_entrada;
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchTerm: any;

  constructor(
    private osService:OsService,
    private pdfService:PdfService
  ) { }

  ngOnInit(): void {
    this.findAllOS();
    this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
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

  applyFilter(): void {
    const search = this.searchTerm.toLowerCase();

    this.dataSource.data = this.ELEMENT_DATA.filter(os =>
      os.numOs?.toString().toLowerCase().includes(search) ||
      os.cliente?.nome.toLowerCase().includes(search) ||
      os.colaborador?.nome.toLowerCase().includes(search) ||
      os.statusOS?.toString().toLowerCase().includes(search) ||
      os.prioridadeOS?.toString().toLowerCase().includes(search)
    );
  }

  imprimirOs(osImp:number){
    debugger;
this.osService.findOsByNumOs(osImp).subscribe(response =>
{
  
  this.pdfService.gerarPdfOsEntrada(response).subscribe((os:Blob) => {
    const url = window.URL.createObjectURL(os);
    const link = document.createElement('a');
    link.href = url
    window.open(url,'_blank')
    link.download = 'Ordem de serviço Nº: '+ response.numOs +'.pdf'
    link.click(); 
  });

})



    
  }
 
  

    
  
  


}
