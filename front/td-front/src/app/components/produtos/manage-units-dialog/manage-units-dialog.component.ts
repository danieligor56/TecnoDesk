import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { UnidadeMedida, UnidadeMedidaService } from 'src/app/services/unidade-medida.service';

@Component({
    selector: 'app-manage-units-dialog',
    templateUrl: './manage-units-dialog.component.html',
    styleUrls: ['./manage-units-dialog.component.css']
})
export class ManageUnitsDialogComponent implements OnInit {
    units: UnidadeMedida[] = [];
    newUnit: UnidadeMedida = { nome: '', sigla: '' };
    isEditing = false;

    constructor(
        private service: UnidadeMedidaService,
        private toast: ToastrService,
        public dialogRef: MatDialogRef<ManageUnitsDialogComponent>
    ) { }

    ngOnInit(): void {
        this.loadUnits();
    }

    loadUnits() {
        this.service.listar().subscribe(res => {
            this.units = res;
        });
    }

    save() {
        if (this.isEditing && this.newUnit.id) {
            this.service.atualizar(this.newUnit.id, this.newUnit).subscribe(() => {
                this.toast.success("Unidade atualizada");
                this.cancelEdit();
                this.loadUnits();
            });
        } else {
            this.service.criar(this.newUnit).subscribe(() => {
                this.toast.success("Unidade criada");
                this.newUnit = { nome: '', sigla: '' };
                this.loadUnits();
            });
        }
    }

    edit(unit: UnidadeMedida) {
        this.newUnit = { ...unit };
        this.isEditing = true;
    }

    cancelEdit() {
        this.newUnit = { nome: '', sigla: '' };
        this.isEditing = false;
    }

    delete(unit: UnidadeMedida) {
        if (confirm(`Excluir unidade ${unit.nome}?`)) {
            this.service.excluir(unit.id!).subscribe(() => {
                this.toast.success("Unidade excluída");
                this.loadUnits();
            }, err => {
                this.toast.error("Não foi possível excluir (pode estar em uso)");
            });
        }
    }
}
