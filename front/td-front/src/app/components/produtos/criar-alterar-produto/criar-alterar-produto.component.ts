import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { StepperOrientation } from '@angular/material/stepper';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Produtos } from 'src/app/models/Produtos';
import { ProdutosService } from 'src/app/services/produtos.service';
import { UnidadeMedida, UnidadeMedidaService } from 'src/app/services/unidade-medida.service';
import { CategoriaProduto, CategoriaProdutoService } from 'src/app/services/categoria-produto.service';
import { ManageUnitsDialogComponent } from '../manage-units-dialog/manage-units-dialog.component';
import { ManageCategoriesDialogComponent } from '../manage-categories-dialog/manage-categories-dialog.component';


@Component({
  selector: 'app-criar-alterar-produto',
  templateUrl: './criar-alterar-produto.component.html',
  styleUrls: ['./criar-alterar-produto.component.css']
})
export class CriarAlterarProdutoComponent implements OnInit {

  checked = this.data.eNovoProduto ? true : this.data.produto?.produtoAtivo;
  disabled = false;
  selected = 'option2';
  btn1valid: boolean = false;
  btn2valid: boolean = false;
  activePass: boolean = false;
  produtoId: number | undefined = this.data.produto?.id;

  unidades: UnidadeMedida[] = [];
  categorias: CategoriaProduto[] = [];

  firstFormGroup = this._formBuilder.group({

    nome: ['', Validators.required],
    descricao: ['', Validators.required],
    marca: ['',],
    codigo_barras: ['',],
    categoria: ['', Validators.required],
    produtoAtivo: [this.checked, Validators.required]

  });

  secondFormGroup = this._formBuilder.group({
    preco: ['', Validators.required],
    precoCusto: ['']
  });

  thirdFormGroup = this._formBuilder.group({

    unidadeMedida: ['', Validators.required],
    quantidadeEstoque: ['', Validators.required]

  });
  stepperOrientation: Observable<StepperOrientation>;

  constructor(private _formBuilder: FormBuilder,
    breakpointObserver: BreakpointObserver,
    private produtosService: ProdutosService,
    private unidadeService: UnidadeMedidaService,
    private categoriaService: CategoriaProdutoService,
    private toast: ToastrService,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<CriarAlterarProdutoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { eNovoProduto: boolean, produto?: Produtos }
  ) {
    this.stepperOrientation = breakpointObserver.observe('(min-width: 800px)')
      .pipe(map(({ matches }) => matches ? 'horizontal' : 'vertical'));



  }

  validarBtn1() {
    if (this.firstFormGroup.get('nome')?.valid) {
      this.btn1valid = true;
      this.activePass = true
    } else {
      this.btn1valid = false;
      this.activePass = false;
    }

  }

  validarBtn2() {
    if (this.secondFormGroup.get('preco')?.valid) {
      this.btn2valid = true;
      this.activePass = true
    } else {
      this.btn2valid = false;
      this.activePass = false;
    }

  }

  criarNovoProduto() {
    if (this.firstFormGroup.get('nome')?.valid &&
      this.firstFormGroup.get('descricao')?.valid &&
      this.secondFormGroup.get('preco')?.valid
    ) {

      const novoProduto: any = {
        nome: this.firstFormGroup.get('nome')?.value || '',
        descricao: this.firstFormGroup.get('descricao')?.value || '',
        marca: this.firstFormGroup.get('marca')?.value || '',
        codigo_barras: this.firstFormGroup.get('codigo_barras')?.value,
        produtoAtivo: this.firstFormGroup.get('produtoAtivo')?.value || false,
        preco: this.secondFormGroup.get('preco')?.value || 0,
        precoCusto: this.secondFormGroup.get('precoCusto')?.value,
        unidadeMedida: this.thirdFormGroup.get('unidadeMedida')?.value,
        categoria: this.firstFormGroup.get('categoria')?.value,
        quantidadeEstoque: this.thirdFormGroup.get('quantidadeEstoque')?.value || 0
      }

      this.produtosService.criarNovoProduto(novoProduto).subscribe(response => {
        if (response) {
          this.dialogRef.close(true);
        } else {
          this.toast.error("Erro ao criar produto. ")
        }
      })
    }
  }

  alterarProduto() {
    // Validação mínima: nome, descrição e preço são obrigatórios
    if (this.firstFormGroup.get('nome')?.valid &&
      this.firstFormGroup.get('descricao')?.valid &&
      this.secondFormGroup.get('preco')?.valid &&
      this.produtoId
    ) {

      // Se unidadeMedida ou quantidadeEstoque não estiverem preenchidos, usa os valores do produto original
      const produtoOriginal = this.data.produto;

      const produtoAlterado: any = {
        id: this.produtoId,
        nome: this.firstFormGroup.get('nome')?.value || '',
        descricao: this.firstFormGroup.get('descricao')?.value || '',
        marca: this.firstFormGroup.get('marca')?.value || '',
        codigo_barras: this.firstFormGroup.get('codigo_barras')?.value,
        produtoAtivo: this.firstFormGroup.get('produtoAtivo')?.value || false,
        preco: this.secondFormGroup.get('preco')?.value || 0,
        precoCusto: this.secondFormGroup.get('precoCusto')?.value,
        unidadeMedida: this.thirdFormGroup.get('unidadeMedida')?.value || produtoOriginal?.unidadeMedida?.id,
        categoria: this.firstFormGroup.get('categoria')?.value || produtoOriginal?.categoria?.id,
        quantidadeEstoque: this.thirdFormGroup.get('quantidadeEstoque')?.value || produtoOriginal?.quantidadeEstoque || 0
      }

      this.produtosService.alterarProduto(this.produtoId, produtoAlterado).subscribe(response => {
        if (response) {
          this.dialogRef.close(true);
        } else {
          this.toast.error("Erro ao alterar produto. ")
        }
      })
    } else {
      this.toast.warning("Preencha os campos obrigatórios: Nome, Descrição e Preço")
    }
  }

  salvarProduto() {
    if (this.data.eNovoProduto) {
      this.criarNovoProduto();
    } else {
      this.alterarProduto();
    }
  }

  isFormularioValido(): boolean {
    // Valida os campos obrigatórios básicos
    const nomeValido = this.firstFormGroup.get('nome')?.valid;
    const descricaoValida = this.firstFormGroup.get('descricao')?.valid;
    const precoValido = this.secondFormGroup.get('preco')?.valid;
    const categoriaValida = this.firstFormGroup.get('categoria')?.valid;
    const unidadeMedidaValida = this.thirdFormGroup.get('unidadeMedida')?.valid;
    const quantidadeEstoqueValida = this.thirdFormGroup.get('quantidadeEstoque')?.valid;

    return !!(nomeValido && descricaoValida && precoValido && categoriaValida && unidadeMedidaValida && quantidadeEstoqueValida);
  }

  podeSalvarNoPasso1(): boolean {
    return !!(this.firstFormGroup.get('nome')?.valid &&
      this.firstFormGroup.get('descricao')?.valid &&
      this.firstFormGroup.get('categoria')?.valid);
  }

  podeSalvarNoPasso2(): boolean {
    return !!(this.firstFormGroup.get('nome')?.valid &&
      this.firstFormGroup.get('descricao')?.valid &&
      this.firstFormGroup.get('categoria')?.valid &&
      this.secondFormGroup.get('preco')?.valid);
  }

  podeSalvarNoPasso3(): boolean {
    // Na edição, no último passo, valida apenas os campos obrigatórios básicos
    // Os campos de estoque podem usar valores originais se não preenchidos
    return !!(this.firstFormGroup.get('nome')?.valid &&
      this.firstFormGroup.get('descricao')?.valid &&
      this.secondFormGroup.get('preco')?.valid);
  }








  ngOnInit(): void {
    this.carregarUnidades();
    this.carregarCategorias();
    // Se for edição, carrega os dados do produto nos formulários
    if (!this.data.eNovoProduto && this.data.produto) {
      const produto = this.data.produto;

      this.firstFormGroup.patchValue({
        nome: produto.nome || '',
        descricao: produto.descricao || '',
        marca: produto.marca || '',
        codigo_barras: produto.codigo_barras || '',
        categoria: (produto.categoria as any)?.id || '',
        produtoAtivo: produto.produtoAtivo !== undefined ? produto.produtoAtivo : true
      });

      this.secondFormGroup.patchValue({
        preco: produto.preco || '',
        precoCusto: produto.precoCusto || ''
      });

      this.thirdFormGroup.patchValue({
        unidadeMedida: (produto.unidadeMedida as any)?.id || '',
        quantidadeEstoque: produto.quantidadeEstoque || ''
      });

      // Valida os botões após carregar os dados
      this.validarBtn1();
      this.validarBtn2();
    }
  }



  carregarUnidades() {
    this.unidadeService.listar().subscribe(res => {
      this.unidades = res;
    });
  }

  carregarCategorias() {
    this.categoriaService.listar().subscribe(res => {
      this.categorias = res;
    });
  }

  gerenciarUnidades() {
    const dialogRef = this.dialog.open(ManageUnitsDialogComponent, {
      width: '500px'
    });
    dialogRef.afterClosed().subscribe(() => {
      this.carregarUnidades();
    });
  }

  gerenciarCategorias() {
    const dialogRef = this.dialog.open(ManageCategoriesDialogComponent, {
      width: '500px'
    });
    dialogRef.afterClosed().subscribe(() => {
      this.carregarCategorias();
    });
  }

}
