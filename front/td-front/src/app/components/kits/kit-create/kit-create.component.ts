import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { ProdutosService } from 'src/app/services/produtos.service';
import { Produtos } from 'src/app/models/Produtos';
import { OrcamentoService } from 'src/app/services/orcamento.service';
import { OrcamentoItem } from 'src/app/models/OrcamentoItem';
import { ToastrService } from 'ngx-toastr';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-kit-create',
  templateUrl: './kit-create.component.html',
  styleUrls: ['./kit-create.component.css']
})
export class KitCreateComponent implements OnInit {
  produto: Produtos[] = [];
  displayedColumns: string[] = ['select', 'id', 'nome', 'preco'];
  dataSource = new MatTableDataSource<Produtos>(this.produto);
  selection = new SelectionModel<Produtos>(true, []);
  codOrcamento: number = 0;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private dialogRef: MatDialogRef<KitCreateComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private produtoService: ProdutosService,
    private orcamentoService: OrcamentoService,
    private toast: ToastrService
  ) { }

  ngOnInit(): void {
    this.encontrarProdutos();
  }

  encontrarProdutos() {
    this.produtoService.listarProdutos().subscribe(response => {
      this.produto = response;
      this.dataSource = new MatTableDataSource<Produtos>(response);
      this.dataSource.paginator = this.paginator;
    });
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

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  getTotalValor(): number {
    return this.selection.selected.reduce((sum, p) => sum + (p.preco || 0), 0);
  }

  get selectedProductNames(): string {
    return this.selection.selected.map(p => p.nome).join(', ');
  }

  criarKit() {
    if (this.selection.selected.length === 0) {
      this.toast.warning('Selecione pelo menos um produto para criar o kit.');
      return;
    }

    const numbOs = Number(this.data.id);

    this.orcamentoService.buscarPorId(numbOs).subscribe(orcamento => {
      this.codOrcamento = orcamento.id;

      const nomes = this.selection.selected.map(p => p.nome).join(' + ');
      const totalValor = this.selection.selected.reduce((sum, p) => sum + (p.preco || 0), 0);

      const kitItem: OrcamentoItem = {
        empresa: this.selection.selected[0].empresa, // assuming all products have same empresa
        codOrcamento: this.codOrcamento,
        codigoItem: 0, // temporary id for kit
        nomeServicoAvulso: `Kit: ${nomes}`,
        descricaoServicoAvulso: `Kit composto por: ${nomes}`,
        valorUnidadeAvulso: totalValor,
        valorHoraAvulso: 0,
        descontoServico: 0,
        isAvulso: true,
        produtoOuServico: 1 // PRODUTO
      };

      this.orcamentoService.inserirItem(this.codOrcamento, kitItem).subscribe({
        next: () => {
          this.toast.success('Kit criado e adicionado ao orçamento!');
          this.dialogRef.close(true);
        },
        error: err => {
          console.error('Erro ao criar kit:', err);
          this.toast.error('Erro ao criar kit.');
        }
      });
    });
  }
}
