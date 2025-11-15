import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ControleEstoque } from 'src/app/models/ControleEstoque';
import { TipoMovimentacao } from 'src/app/models/MovimentacaoEstoque';
import { ControleEstoqueService } from 'src/app/services/controle-estoque.service';
import { ProdutosService } from 'src/app/services/produtos.service';
import { Produtos } from 'src/app/models/Produtos';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-movimentacao-estoque',
  templateUrl: './movimentacao-estoque.component.html',
  styleUrls: ['./movimentacao-estoque.component.css']
})
export class MovimentacaoEstoqueComponent implements OnInit {
  movimentacaoForm: FormGroup;
  produtos: Produtos[] = [];
  tiposMovimentacao = TipoMovimentacao;
  tipoMovimentacaoKeys = Object.keys(TipoMovimentacao);

  constructor(
    private formBuilder: FormBuilder,
    private controleEstoqueService: ControleEstoqueService,
    private produtosService: ProdutosService,
    private toast: ToastrService,
    public dialogRef: MatDialogRef<MovimentacaoEstoqueComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { controleEstoque?: ControleEstoque }
  ) {
    this.movimentacaoForm = this.formBuilder.group({
      produtoId: ['', Validators.required],
      tipoMovimentacao: ['', Validators.required],
      quantidade: ['', [Validators.required, Validators.min(0.01)]],
      observacao: [''],
      origemMovimentacao: ['']
    });
  }

  ngOnInit(): void {
    this.carregarProdutos();
    
    if (this.data?.controleEstoque?.produto) {
      this.movimentacaoForm.patchValue({
        produtoId: this.data.controleEstoque.produto.id
      });
      this.movimentacaoForm.get('produtoId')?.disable();
    }
  }

  carregarProdutos() {
    this.produtosService.listarProdutos().subscribe(
      response => {
        this.produtos = response;
      },
      error => {
        this.toast.error("Erro ao carregar produtos");
      }
    );
  }

  salvarMovimentacao() {
    if (this.movimentacaoForm.valid) {
      const formValue = this.movimentacaoForm.getRawValue();
      const usuario = sessionStorage.getItem('usuario') || 'Sistema';
      
      const movimentacao = {
        produtoId: formValue.produtoId,
        tipoMovimentacao: formValue.tipoMovimentacao,
        quantidade: formValue.quantidade,
        observacao: formValue.observacao,
        usuarioResponsavel: usuario,
        origemMovimentacao: formValue.origemMovimentacao || 'Manual'
      };

      this.controleEstoqueService.realizarMovimentacao(movimentacao).subscribe(
        response => {
          this.toast.success("Movimentação realizada com sucesso");
          this.dialogRef.close(true);
        },
        error => {
          this.toast.error(error.error?.message || "Erro ao realizar movimentação");
        }
      );
    } else {
      this.toast.warning("Preencha todos os campos obrigatórios");
    }
  }

  cancelar() {
    this.dialogRef.close(false);
  }

  getTipoMovimentacaoLabel(tipo: string): string {
    switch(tipo) {
      case 'ENTRADA': return 'Entrada';
      case 'SAIDA': return 'Saída';
      case 'AJUSTE': return 'Ajuste';
      default: return tipo;
    }
  }

}
