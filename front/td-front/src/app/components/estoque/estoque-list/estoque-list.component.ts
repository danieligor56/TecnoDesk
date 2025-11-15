import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ControleEstoque } from 'src/app/models/ControleEstoque';
import { ControleEstoqueService } from 'src/app/services/controle-estoque.service';
import { MovimentacaoEstoqueComponent } from '../movimentacao-estoque/movimentacao-estoque.component';
import { ConfigurarEstoqueComponent } from '../configurar-estoque/configurar-estoque.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-estoque-list',
  templateUrl: './estoque-list.component.html',
  styleUrls: ['./estoque-list.component.css']
})
export class EstoqueListComponent implements OnInit {
  controlesEstoque: ControleEstoque[] = [];
  dataSource = new MatTableDataSource<ControleEstoque>(this.controlesEstoque);
  @ViewChild(MatPaginator) paginator: MatPaginator;
  displayedColumns: string[] = ['produto', 'estoqueAtual', 'estoqueMinimo', 'estoqueMaximo', 'status', 'acoes'];
  
  constructor(
    private controleEstoqueService: ControleEstoqueService,
    private dialog: MatDialog,
    private toast: ToastrService
  ) { }

  ngOnInit(): void {
    this.carregarControleEstoque();
  }

  carregarControleEstoque() {
    this.controleEstoqueService.listarControleEstoque().subscribe(
      response => {
        this.controlesEstoque = response;
        this.dataSource = new MatTableDataSource<ControleEstoque>(response);
        this.dataSource.paginator = this.paginator;
      },
      error => {
        this.toast.error("Erro ao carregar controle de estoque");
      }
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  getStatusEstoque(controle: ControleEstoque): string {
    if (controle.estoqueAtual <= controle.estoqueMinimo) {
      return 'CRÍTICO';
    } else if (controle.estoqueMaximo && controle.estoqueAtual >= controle.estoqueMaximo) {
      return 'CHEIO';
    }
    return 'NORMAL';
  }

  getStatusClass(controle: ControleEstoque): string {
    if (controle.estoqueAtual <= controle.estoqueMinimo) {
      return 'status-critico';
    } else if (controle.estoqueMaximo && controle.estoqueAtual >= controle.estoqueMaximo) {
      return 'status-cheio';
    }
    return 'status-normal';
  }

  openMovimentacao(controle?: ControleEstoque) {
    const dialogRef = this.dialog.open(MovimentacaoEstoqueComponent, {
      width: '600px',
      data: {
        controleEstoque: controle
      }
    });
    dialogRef.afterClosed().subscribe(response => {
      if (response) {
        this.carregarControleEstoque();
        this.toast.success("Movimentação realizada com sucesso");
      }
    });
  }

  openConfigurar(controle: ControleEstoque) {
    const dialogRef = this.dialog.open(ConfigurarEstoqueComponent, {
      width: '500px',
      data: {
        controleEstoque: controle
      }
    });
    dialogRef.afterClosed().subscribe(response => {
      if (response) {
        this.carregarControleEstoque();
        this.toast.success("Configuração atualizada com sucesso");
      }
    });
  }

  verificarEstoqueBaixo() {
    this.controleEstoqueService.listarProdutosEstoqueBaixo().subscribe(
      response => {
        if (response.length > 0) {
          this.toast.warning(`${response.length} produto(s) com estoque abaixo do mínimo!`, "Atenção");
        } else {
          this.toast.info("Todos os produtos estão com estoque adequado");
        }
      },
      error => {
        this.toast.error("Erro ao verificar estoque baixo");
      }
    );
  }

}
