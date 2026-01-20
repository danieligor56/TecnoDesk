import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CategoriaProduto, CategoriaProdutoService } from 'src/app/services/categoria-produto.service';

@Component({
    selector: 'app-manage-categories-dialog',
    templateUrl: './manage-categories-dialog.component.html',
    styleUrls: ['./manage-categories-dialog.component.css']
})
export class ManageCategoriesDialogComponent implements OnInit {
    categories: CategoriaProduto[] = [];
    newCategory: CategoriaProduto = { nome: '' };
    isEditing = false;

    constructor(
        private service: CategoriaProdutoService,
        private toast: ToastrService,
        public dialogRef: MatDialogRef<ManageCategoriesDialogComponent>
    ) { }

    ngOnInit(): void {
        this.loadCategories();
    }

    loadCategories() {
        this.service.listar().subscribe(res => {
            this.categories = res;
        });
    }

    save() {
        if (this.isEditing && this.newCategory.id) {
            this.service.atualizar(this.newCategory.id, this.newCategory).subscribe(() => {
                this.toast.success("Categoria atualizada");
                this.cancelEdit();
                this.loadCategories();
            });
        } else {
            this.service.criar(this.newCategory).subscribe(() => {
                this.toast.success("Categoria criada");
                this.newCategory = { nome: '' };
                this.loadCategories();
            });
        }
    }

    edit(cat: CategoriaProduto) {
        this.newCategory = { ...cat };
        this.isEditing = true;
    }

    cancelEdit() {
        this.newCategory = { nome: '' };
        this.isEditing = false;
    }

    delete(cat: CategoriaProduto) {
        if (confirm(`Excluir categoria ${cat.nome}?`)) {
            this.service.excluir(cat.id!).subscribe(() => {
                this.toast.success("Categoria excluída");
                this.loadCategories();
            }, err => {
                this.toast.error("Não foi possível excluir (pode estar em uso)");
            });
        }
    }
}
