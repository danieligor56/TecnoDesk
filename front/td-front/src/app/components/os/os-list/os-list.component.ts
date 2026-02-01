import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Os_entrada } from 'src/app/models/Os-entrada';
import { OsService } from 'src/app/services/os.service';
import { PdfService } from 'src/app/services/pdf.service';
import { ToastrService } from 'ngx-toastr';
import { OsManagerComponent } from '../os-manager/os-manager.component';
import { ConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';


@Component({
  selector: 'app-os-list',
  templateUrl: './os-list.component.html',
  styleUrls: ['./os-list.component.css']
})

export class OsListComponent implements OnInit {
  ELEMENT_DATA: Os_entrada[] = [];
  dataSource = new MatTableDataSource<Os_entrada>(this.ELEMENT_DATA);
  displayedColumns: string[] = ['numOs', 'cliente', 'colaborador', 'statusOS', 'prioridadeOS', 'acoesOs'];
  filteredELEMENT_DATA: Os_entrada[] = [];
  osOne: Os_entrada;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchTerm: any;
  isLoading = false;
  showAll: boolean = false;

  constructor(
    private osService: OsService,
    private pdfService: PdfService,
    private toast: ToastrService,
    private dialog: MatDialog

  ) { }

  ngOnInit(): void {
    this.isLoading = true;
    this.findAllOS();
    this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
  }

  findAllOS() {
    debugger
    this.isLoading = true;
    const request = this.showAll
      ? this.osService.listarOsCanceladasEncerrada()
      : this.osService.findAllOs();

    request.subscribe(
      response => {
        this.ELEMENT_DATA = response;
        this.dataSource = new MatTableDataSource<Os_entrada>(response);
        this.dataSource.paginator = this.paginator;
        this.isLoading = false;
      }, error => {
        this.isLoading = false;
      }
    )
  }

  toggleShowAll() {
    this.findAllOS();
  }

  applyFilter(): void {
    const search = this.searchTerm.toLowerCase();

    this.dataSource.data = this.ELEMENT_DATA.filter(os =>
      os.sequencial?.toString().toLowerCase().includes(search) ||
      os.cliente?.nome.toLowerCase().includes(search) ||
      os.colaborador?.nome.toLowerCase().includes(search) ||
      os.statusOS?.toString().toLowerCase().includes(search) ||
      os.prioridadeOS?.toString().toLowerCase().includes(search)

    );
  }

  imprimirOs(osImp: number) {
    debugger;
    this.osService.findOsByNumOs(osImp).subscribe(response => {

      this.pdfService.gerarPdfOsEntrada(response).subscribe((os: Blob) => {
        const url = window.URL.createObjectURL(os);
        const link = document.createElement('a');
        link.href = url
        window.open(url, '_blank')
        link.download = 'Ordem de serviço Nº: ' + response.sequencial + '.pdf'
        link.click();
      });

    })




  }

  cancelarOs(numOs: number) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Cancelar OS',
        message: `Deseja realmente cancelar a OS Nº ${numOs}? Caso você cancele, o valor não será contabilizado para o financeiro.`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.osService.alterarStatusOS(numOs, 8).subscribe({
          next: () => {
            this.toast.success("OS cancelada com sucesso.");
            this.findAllOS();
          },
          error: (err) => {
            this.toast.error("Falha ao cancelar a OS: " + (err?.error?.message || "Erro desconhecido"));
          }
        });
      }
    });
  }

  reabrirOs(numOs: number) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Reabrir OS',
        message: `Deseja realmente reabrir a OS Nº ${numOs}?`
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.osService.alterarStatusOS(numOs, 0).subscribe({
          next: () => {
            this.toast.success("OS reaberta com sucesso.");
            this.findAllOS();
          },
          error: (err) => {
            this.toast.error("Falha ao reabrir a OS: " + (err?.error?.message || "Erro desconhecido"));
          }
        });
      }
    });
  }

  getStatusClass(status: string): string {
    if (!status) return 'status-badge';
    const statusUpper = status.toUpperCase().trim();
    return `status-badge status-${statusUpper}`;
  }

  getStatusLabel(status: string): string {
    if (!status) return status;

    const statusMap: { [key: string]: string } = {
      'NOVO': 'Nova',
      'EM_ANDAMENTO': 'Em Andamento',
      'AGUARDANDO_RESP_ORCAMENTO': 'Aguardando Aprovação do Orçamento',
      'AGUARDANDO_PECAS': 'Aguardando Peças / Materiais',
      'AGUARDANDO_RETIRADA': 'Aguardando Cliente Retirar',
      'ORCAMENTO_APROVADO': 'Orçamento Aprovado',
      'PENDENTE': 'Pendente',
      'CONCLUIDO': 'Concluída',
      'CANCELADA': 'Cancelada',
      'ENCERRADA': 'Encerrada'
    };

    return statusMap[status.toUpperCase().trim()] || status;
  }

  getPriorityClass(priority: string): string {
    if (!priority) return 'priority-badge';
    const priorityUpper = priority.toUpperCase().trim();
    return `priority-badge priority-${priorityUpper}`;
  }

  getPriorityLabel(priority: string): string {
    if (!priority) return priority;

    const priorityMap: { [key: string]: string } = {
      'NORMAL': 'Normal',
      'URGENCIA': 'Crítica / Urgente',
      'GARANTIA': 'Garantia',
      'PRIORITARIA': 'Prioritária'
    };

    return priorityMap[priority.toUpperCase().trim()] || priority;
  }

  getPriorityIcon(priority: string): string {
    if (!priority) return 'label';

    const iconMap: { [key: string]: string } = {
      'NORMAL': 'check_circle',
      'URGENCIA': 'local_fire_department',
      'GARANTIA': 'autorenew',
      'PRIORITARIA': 'schedule'
    };

    return iconMap[priority.toUpperCase().trim()] || 'label';
  }

}
