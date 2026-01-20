import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { VisitaTecnica, VisitaTecnicaService, StatusVisita } from 'src/app/services/visita-tecnica.service';
import { VisitaTecnicaDialogComponent } from './visita-tecnica-dialog/visita-tecnica-dialog.component';

@Component({
    selector: 'app-visita-tecnica',
    templateUrl: './visita-tecnica.component.html',
    styleUrls: ['./visita-tecnica.component.css']
})
export class VisitaTecnicaComponent implements OnInit {

    visitas: VisitaTecnica[] = [];
    dataSource = new MatTableDataSource<VisitaTecnica>(this.visitas);

    @ViewChild(MatPaginator) paginator: MatPaginator;

    displayedColumns: string[] = ['sequencial', 'cliente', 'tecnico', 'dataHora', 'tipo', 'status', 'acoes'];

    constructor(
        private service: VisitaTecnicaService,
        private dialog: MatDialog,
        private toast: ToastrService
    ) { }

    ngOnInit(): void {
        this.listarVisitas();
    }

    listarVisitas() {
        this.service.listar().subscribe(res => {
            this.visitas = res;
            this.dataSource = new MatTableDataSource<VisitaTecnica>(res);
            this.dataSource.paginator = this.paginator;
        }, err => {
            this.toast.error("Erro ao listar visitas técnicas");
        });
    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }

    openCriarDialog() {
        const dialogRef = this.dialog.open(VisitaTecnicaDialogComponent, {
            width: '600px',
            data: { isNew: true }
        });

        dialogRef.afterClosed().subscribe(res => {
            if (res) this.listarVisitas();
        });
    }

    openEditarDialog(visita: VisitaTecnica) {
        const dialogRef = this.dialog.open(VisitaTecnicaDialogComponent, {
            width: '600px',
            data: { isNew: false, visita: visita }
        });

        dialogRef.afterClosed().subscribe(res => {
            if (res) this.listarVisitas();
        });
    }

    excluir(visita: VisitaTecnica) {
        if (confirm(`Deseja realmente excluir a visita #${visita.sequencial}?`)) {
            this.service.excluir(visita.id!).subscribe(() => {
                this.toast.success("Visita excluída com sucesso");
                this.listarVisitas();
            });
        }
    }

    getStatusClass(status: string): string {
        switch (status) {
            case 'AGENDADA': return 'status-agendada';
            case 'EM_ANDAMENTO': return 'status-andamento';
            case 'FINALIZADA': return 'status-finalizada';
            case 'CANCELADA': return 'status-cancelada';
            default: return '';
        }
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
