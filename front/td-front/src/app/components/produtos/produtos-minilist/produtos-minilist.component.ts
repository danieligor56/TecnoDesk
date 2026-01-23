
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ItemService } from 'src/app/models/ItemService';
import { ItemServiceService } from 'src/app/services/item-service.service';
import { OrcamentoItem } from 'src/app/models/OrcamentoItem';
import { OrcamentoService } from 'src/app/services/orcamento.service';
import { switchMap } from 'rxjs/operators';
import { ItemServicelMinilistComponent } from '../../item-service/item-servicel-minilist/item-servicel-minilist.component';
import { ItemServiceCreateComponent } from '../../item-service/item-service-create/item-service-create.component';
import { ItemServiceCobrarhoraComponent } from '../../item-service/item-service-cobrarhora/item-service-cobrarhora.component';
import { ProdutosService } from 'src/app/services/produtos.service';
import { Produtos } from 'src/app/models/Produtos';
import { QuantityDialogComponent } from '../../shared/quantity-dialog/quantity-dialog.component';

@Component({
  selector: 'app-produtos-minilist',
  templateUrl: './produtos-minilist.component.html',
  styleUrls: ['./produtos-minilist.component.css']
})

export class ProdutosMinilistComponent implements OnInit {
  servico: ItemService[] = [];
  produto: Produtos[] = [];
  displayedColumns: string[] = ['id', 'progress', 'name', 'fruit', 'add'];
  dataSource = new MatTableDataSource<Produtos>(this.produto);
  servicoContrato: OrcamentoItem;
  codOrcamento: number = 0;
  valorHorasServico: number = 0;


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

    this.produtoService.listarProdutos().subscribe(response => {
      this.produto = response;
      this.dataSource = new MatTableDataSource<Produtos>(this.produto);
      this.dataSource.paginator = this.paginator;

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

  encontrarOrcamento() { }

  adicionarServico(id: number, cobrarPorUnd: boolean) {
    debugger;

    const produtoSelecionado = this.produto.find(p => p.id === id);
    const nomeProduto = produtoSelecionado ? produtoSelecionado.nome : 'Produto';

    const dialogRef = this.dialog.open(QuantityDialogComponent, {
      data: { nomeItem: nomeProduto },
      width: '350px'
    });

    dialogRef.afterClosed().subscribe(quantidade => {
      if (quantidade && quantidade > 0) {
        this.executarAdicao(id, cobrarPorUnd, quantidade);
      }
    });
  }

  private executarAdicao(id: number, cobrarPorUnd: boolean, quantidade: number) {
    const numbOs = Number(this.data.id);

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
        console.error("Erro ao adicionar produto:", err);
      }
    });
  }

}
