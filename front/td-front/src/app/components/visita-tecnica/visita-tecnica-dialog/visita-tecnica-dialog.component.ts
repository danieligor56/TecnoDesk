import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { VisitaTecnica, VisitaTecnicaService, StatusVisita, TipoVisita } from 'src/app/services/visita-tecnica.service';
import { ClienteService } from 'src/app/services/cliente.service';
import { ColaboradorService } from 'src/app/services/colaborador.service';
import { Cliente } from 'src/app/models/Cliente';
import { Colaborador } from 'src/app/models/Colaborador';

@Component({
    selector: 'app-visita-tecnica-dialog',
    templateUrl: './visita-tecnica-dialog.component.html',
    styleUrls: ['./visita-tecnica-dialog.component.css']
})
export class VisitaTecnicaDialogComponent implements OnInit {

    visitaForm: FormGroup;
    clientes: Cliente[] = [];
    tecnicos: Colaborador[] = [];

    statusOpcoes = Object.values(StatusVisita);
    tipoOpcoes = Object.values(TipoVisita);

    constructor(
        private fb: FormBuilder,
        private service: VisitaTecnicaService,
        private clienteService: ClienteService,
        private colaboradorService: ColaboradorService,
        private toast: ToastrService,
        public dialogRef: MatDialogRef<VisitaTecnicaDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: { isNew: boolean, visita?: VisitaTecnica }
    ) { }

    ngOnInit(): void {
        this.initForm();
        this.carregarClientes();
        this.carregarTecnicos();
    }

    initForm() {
        const v = this.data.visita;
        this.visitaForm = this.fb.group({
            clienteId: [v?.clienteId || '', Validators.required],
            tecnicoId: [v?.tecnicoId || '', Validators.required],
            dataVisita: [v?.dataVisita || '', Validators.required],
            horaVisita: [v?.horaVisita || '', Validators.required],
            tipoVisita: [v?.tipoVisita || TipoVisita.PREVENTIVA, Validators.required],
            statusVisita: [v?.statusVisita || StatusVisita.AGENDADA, Validators.required],
            enderecoVisita: [v?.enderecoVisita || ''],
            observacoes: [v?.observacoes || ''],
            relatorioTecnico: [v?.relatorioTecnico || ''],
            proximaVisitaSugerida: [v?.proximaVisitaSugerida || '']
        });
    }

    carregarClientes() {
        this.clienteService.findAll().subscribe(res => {
            this.clientes = res;
        });
    }

    carregarTecnicos() {
        this.colaboradorService.listarTecnicos().subscribe(res => {
            this.tecnicos = res;
        });
    }

    salvar() {
        if (this.visitaForm.invalid) return;

        const dados = this.visitaForm.value;
        if (this.data.isNew) {
            this.service.criar(dados).subscribe(() => {
                this.toast.success("Visita técnica agendada com sucesso");
                this.dialogRef.close(true);
            }, err => {
                this.toast.error("Erro ao agendar visita: " + err.error?.message);
            });
        } else {
            this.service.atualizar(this.data.visita!.id!, dados).subscribe(() => {
                this.toast.success("Visita técnica atualizada com sucesso");
                this.dialogRef.close(true);
            }, err => {
                this.toast.error("Erro ao atualizar visita: " + err.error?.message);
            });
        }
    }

    cancelar() {
        this.dialogRef.close();
    }

    traduzirStatus(status: string): string {
        switch (status) {
            case 'AGENDADA': return 'Agendada';
            case 'EM_ANDAMENTO': return 'Em Andamento';
            case 'FINALIZADA': return 'Finalizada';
            case 'CANCELADA': return 'Cancelada';
            default: return status;
        }
    }

    traduzirTipo(tipo: string): string {
        switch (tipo) {
            case 'PREVENTIVA': return 'Preventiva';
            case 'CORRETIVA': return 'Corretiva';
            case 'INSTALACAO': return 'Instalação';
            case 'VISTORIA': return 'Vistoria';
            case 'OUTROS': return 'Outros';
            default: return tipo;
        }
    }
}
