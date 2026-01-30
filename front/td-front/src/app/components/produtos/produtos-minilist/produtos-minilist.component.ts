
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ItemService } from 'src/app/models/ItemService';
import { ItemServiceService } from 'src/app/services/item-service.service';
import { OrcamentoItem } from 'src/app/models/OrcamentoItem';
import { OrcamentoService } from 'src/app/services/orcamento.service';
import { switchMap } from 'rxjs/operators';
import { ItemServiceCreateComponent } from '../../item-service/item-service-create/item-service-create.component';
import { ProdutosService } from 'src/app/services/produtos.service';
import { Produtos } from 'src/app/models/Produtos';

@Component({
  selector: 'app-produtos-minilist',
  templateUrl: './produtos-minilist.component.html',
  styleUrls: ['./produtos-minilist.component.css']
})

export class ProdutosMinilistComponent implements OnInit {
  servico: ItemService[] = [];
  produto: any[] = [];
  displayedColumns: string[] = ['id', 'progress', 'name', 'fruit', 'quantidade', 'add'];
  dataSource = new MatTableDataSource<any>(this.produto);
  servicoContrato: OrcamentoItem;
  codOrcamento: number = 0;
  valorHorasServico: number = 0;
  isFetching: boolean = false;
  isInserting: boolean = false;


  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private itemService: ItemServiceService,
    private dialog: MatDialog,
    private dialogRef: MatDialogRef<ProdutosMinilistComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private orcamentoService: OrcamentoService,
    private produtoService: ProdutosService
  ) { }

  ngOnInit(): void {
    this.encontrarProdutos();
  }

  encontrarProdutos() {
    this.isFetching = true;
    this.produtoService.listarProdutos().subscribe(response => {
      this.produto = response.map(p => ({ ...p, quantidade_pedida: 1 }));
      this.dataSource = new MatTableDataSource<any>(this.produto);
      this.dataSource.paginator = this.paginator;
      this.isFetching = false;
    }, error => {
      this.isFetching = false;
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


  closeDialog() {
    this.dialogRef.close();
  }


  openCreatServiceDialog() {

    const dialogRef = this.dialog.open(ItemServiceCreateComponent);
    dialogRef.afterClosed().subscribe(response => {
      if (response)
        this.encontrarProdutos();
    })
  }

  aumentarQtd(produto: any) {
    produto.quantidade_pedida++;
  }

  diminuirQtd(produto: any) {
    if (produto.quantidade_pedida > 1) {
      produto.quantidade_pedida--;
    }
  }

  adicionarServico(id: number, cobrarPorUnd: boolean) {
    debugger;

    const produtoSelecionado = this.produto.find(p => p.id === id);
    const quantidade = produtoSelecionado ? produtoSelecionado.quantidade_pedida : 1;

    const numbOs = Number(this.data.id);
    this.isInserting = true;

    this.orcamentoService.buscarPorId(numbOs).pipe(
      switchMap(orcamento => {
        this.codOrcamento = orcamento.id;
        return this.produtoService.encontrarPorId(id);
      }),
      switchMap(response => {
        this.servicoContrato = {
          empresa: response.empresa,
          codOrcamento: this.codOrcamento,
          codigoItem: Number(response.id) ? Number(response.id) : 0,
          nomeServicoAvulso: response.nome,
          descricaoServicoAvulso: response.descricao,
          valorUnidadeAvulso: cobrarPorUnd ? response.preco : 0,
          valorHoraAvulso: 0,
          isAvulso: false,
          produtoOuServico: 1,
          quantidade: quantidade
        };


        // já retorna o observable de inserirItem
        return this.orcamentoService.inserirItem(this.codOrcamento, this.servicoContrato);
      })
    ).subscribe({
      next: () => {
        this.dialogRef.close(true);
      },
      error: err => {
        this.isInserting = false;
        console.error("Erro ao adicionar produto:", err);
      }
    });
  }

}
