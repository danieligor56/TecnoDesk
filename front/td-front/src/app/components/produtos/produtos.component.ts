import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Produtos } from 'src/app/models/Produtos';
import { ProdutosService } from 'src/app/services/produtos.service';
import { CriarAlterarProdutoComponent } from './criar-alterar-produto/criar-alterar-produto.component';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-produtos',
  templateUrl: './produtos.component.html',
  styleUrls: ['./produtos.component.css']
})
export class ProdutosComponent implements OnInit {
  produtos: Produtos[] = [];
  dataSource = new MatTableDataSource<Produtos>(this.produtos);
  @ViewChild(MatPaginator) paginator: MatPaginator;
  displayedColumns: string[] = ['id','nome','descricao','preco','qtdEstoque','codigo_barras','categoria', 'unidadeMedida','acoes'];
  eNovoProduto: boolean = false;
  constructor(
    private produtosService: ProdutosService,
    private dialog:MatDialog,
    private toast:ToastrService,
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

     openCriarAlterarProduto(){
     const dialogRef = this.dialog.open(CriarAlterarProdutoComponent,{
      data:{
          eNovoProduto: true
      } 
     });
         dialogRef.afterClosed().subscribe( response => {
          if(response){
            this.encontrarProdutos();
            this.toast.success("Produto criado com sucesso")
            
          }
         })
  }


}


