import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ProdutosService } from 'src/app/services/produtos.service';
import { OrcamentoService } from 'src/app/services/orcamento.service';
import { Produtos } from 'src/app/models/Produtos';
import { OrcamentoItem } from 'src/app/models/OrcamentoItem';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-produto-create-avulso',
  templateUrl: './produto-create-avulso.component.html',
  styleUrls: ['./produto-create-avulso.component.css']
})
export class ProdutoCreateAvulsoComponent implements OnInit {
  isProduct: boolean = false;
  produtoOrcamentoForm: FormGroup;
  codOrcamento: number;
  quantidade: number = 1;
  vTotal: number = 0;
  novoProduto: Produtos;
  novoOrcamentoItem: OrcamentoItem;

  constructor(
    private dialogRef: MatDialogRef<ProdutoCreateAvulsoComponent>,
    private fb: FormBuilder,
    private orcamentoService: OrcamentoService,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private dialog: MatDialog,
    private produtoService: ProdutosService,
    private toast: ToastrService,
  ) { }

  ngOnInit(): void {
    this.produtoOrcamentoForm = this.fb.group({
      empresa: [],
      codOrcamento: [],
      codigoItem: [],
      nomeProdutoAvulso: [],
      descricaoProdutoAvulso: [null, Validators.required],
      valorUnidadeAvulso: [null, Validators.required],
      quantidade: [],
      isAvulso: []
    });
  }

  calcValorPorQuantidade() {
    debugger;
    const qty = Number(this.quantidade) || 1;
    const valor = Number(this.produtoOrcamentoForm.get('valorUnidadeAvulso').value) || 0;

    this.vTotal = valor * qty;
  }

  inserirProdutoAvulso() {
    debugger;
    const numbOs = Number(this.data.id);

    if (this.produtoOrcamentoForm.valid) {
      this.orcamentoService.buscarPorId(numbOs).subscribe(orcamento => {
        this.codOrcamento = orcamento.id;
        debugger;

        let vProdutoUnd = this.produtoOrcamentoForm.get('valorUnidadeAvulso').value;

        const qty = Number(this.quantidade) || 1;
        const totalValue = vProdutoUnd * qty;

        if (this.isProduct) {
          const novoProduto: Produtos = {
            nome: this.produtoOrcamentoForm.get('nomeProdutoAvulso').value,
            descricao: this.produtoOrcamentoForm.get('descricaoProdutoAvulso').value,
            marca: '',
            preco: vProdutoUnd,
            precoCusto: vProdutoUnd,
            quantidadeEstoque: 0,
            produtoAtivo: true
          };

          this.produtoService.criarNovoProduto(novoProduto).subscribe(response => {
            if (response.id != null) {
              this.toast.success("Produto cadastrado");
              // Adiciona o produto no Orçamento
              this.novoOrcamentoItem = {
                empresa: orcamento.empresa,
                codOrcamento: orcamento.id,
                codigoItem: Number(response.id),
                nomeServicoAvulso: response.nome,
                descricaoServicoAvulso: response.descricao,
                valorUnidadeAvulso: totalValue,
                valorHoraAvulso: 0,
                isAvulso: true,
                produtoOuServico: 1
              };
              this.orcamentoService.inserirItem(this.codOrcamento, this.novoOrcamentoItem).subscribe(() => {
                this.dialogRef.close(true);
              });
            }
          }, error => {
            this.toast.error("Erro durante a criação do produto, contate o suporte.");
            console.log(error);
          });
        } else {
          this.novoOrcamentoItem = {
            empresa: orcamento.empresa,
            codOrcamento: orcamento.id,
            codigoItem: 0,
            nomeServicoAvulso: '',
            descricaoServicoAvulso: this.produtoOrcamentoForm.get('descricaoProdutoAvulso').value,
            valorUnidadeAvulso: totalValue,
            valorHoraAvulso: 0,
            isAvulso: true,
            produtoOuServico: 1
          };
          this.orcamentoService.inserirItem(this.codOrcamento, this.novoOrcamentoItem).subscribe(() => {
            this.dialogRef.close(true);
          });
        }
      });
    } else {
      this.produtoOrcamentoForm.markAllAsTouched();
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
