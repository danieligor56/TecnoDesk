import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ControleEstoque } from 'src/app/models/ControleEstoque';
import { ControleEstoqueService } from 'src/app/services/controle-estoque.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-configurar-estoque',
  templateUrl: './configurar-estoque.component.html',
  styleUrls: ['./configurar-estoque.component.css']
})
export class ConfigurarEstoqueComponent implements OnInit {
  configForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private controleEstoqueService: ControleEstoqueService,
    private toast: ToastrService,
    public dialogRef: MatDialogRef<ConfigurarEstoqueComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { controleEstoque: ControleEstoque }
  ) {
    this.configForm = this.formBuilder.group({
      estoqueMinimo: ['', [Validators.required, Validators.min(0)]],
      estoqueMaximo: ['', Validators.min(0)],
      observacao: ['']
    });
  }

  ngOnInit(): void {
    if (this.data?.controleEstoque) {
      this.configForm.patchValue({
        estoqueMinimo: this.data.controleEstoque.estoqueMinimo,
        estoqueMaximo: this.data.controleEstoque.estoqueMaximo || '',
        observacao: this.data.controleEstoque.observacao || ''
      });
    }
  }

  salvarConfiguracao() {
    if (this.configForm.valid && this.data?.controleEstoque?.produto) {
      const formValue = this.configForm.value;
      
      const config = {
        produtoId: this.data.controleEstoque.produto.id,
        estoqueMinimo: formValue.estoqueMinimo,
        estoqueMaximo: formValue.estoqueMaximo || null,
        observacao: formValue.observacao
      };

      this.controleEstoqueService.criarOuAtualizarControleEstoque(config).subscribe(
        response => {
          this.toast.success("Configuração salva com sucesso");
          this.dialogRef.close(true);
        },
        error => {
          this.toast.error(error.error?.message || "Erro ao salvar configuração");
        }
      );
    } else {
      this.toast.warning("Preencha todos os campos obrigatórios");
    }
  }

  cancelar() {
    this.dialogRef.close(false);
  }

}
