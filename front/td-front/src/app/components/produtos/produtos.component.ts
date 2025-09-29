import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Produtos } from 'src/app/models/Produtos';
import { ProdutosService } from 'src/app/services/produtos.service';


@Component({
  selector: 'app-produtos',
  templateUrl: './produtos.component.html',
  styleUrls: ['./produtos.component.css']
})
export class ProdutosComponent implements OnInit {
  produtos: Produtos[] = [];
  dataSource = new MatTableDataSource<Produtos>(this.produtos);
  @ViewChild(MatPaginator) paginator: MatPaginator;
  displayedColumns: string[] = ['id','nome','descricao','preco','qtdEstoque','codigo_barras','categoria', 'unidadeMedida'];
  constructor(
    private produtosService: ProdutosService
  ) { }

  ngOnInit(): void {
    this.encontrarProdutos();
  }

  encontrarProdutos(){
      this.produtosService.listarProdutos().subscribe(response => {
            this.produtos = response;
            this.dataSource = new MatTableDataSource<Produtos>(response);
            this.dataSource.paginator = this.paginator;
            
          }
      )
      }

        applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
    }


}


