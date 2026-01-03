import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { OsRapida } from 'src/app/models/OsRapida';
import { OsRapidaService } from 'src/app/services/os-rapida.service';
import { OsRapidaDetailDialogComponent } from './os-rapida-detail-dialog.component';
import { OsRapidaCloseDialogComponent } from './os-rapida-close-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-os-rapida-list',
  templateUrl: './os-rapida-list.component.html',
  styleUrls: ['./os-rapida-list.component.css']
})
export class OsRapidaListComponent implements OnInit, AfterViewInit {
  ELEMENT_DATA: OsRapida[] = [];
  dataSource = new MatTableDataSource<OsRapida>(this.ELEMENT_DATA);
  displayedColumns: string[] = ['clienteNome', 'equipamentoServico', 'problemaRelatado', 'status', 'dataAbertura', 'acoes'];
  searchTerm: string = '';

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private osRapidaService: OsRapidaService,
    private dialog: MatDialog,
    private router: Router,
    private toast: ToastrService,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.findAllOsRapidas();
    this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  findAllOsRapidas() {
    this.osRapidaService.listarOsRapidasPorTecnico().subscribe(
      response => {
        this.ELEMENT_DATA = response;
        this.dataSource = new MatTableDataSource<OsRapida>(response);
        this.dataSource.paginator = this.paginator;
      },
      error => {
        console.error('Erro ao carregar OS rápidas:', error);
      }
    );
  }

  applyFilter(): void {
    const search = this.searchTerm.toLowerCase();
    this.dataSource.data = this.ELEMENT_DATA.filter(os =>
      os.clienteNome?.toLowerCase().includes(search) ||
      os.equipamentoServico?.toLowerCase().includes(search) ||
      os.problemaRelatado?.toLowerCase().includes(search) ||
      os.status?.toLowerCase().includes(search)
    );
  }

  getStatusLabel(status: string): string {
    if (!status) return 'Novo';

    const statusMap: { [key: string]: string } = {
      'NOVO': 'Novo',
      'EM_ANDAMENTO': 'Em Andamento',
      'AGUARDANDO_RESP_ORCAMENTO': 'Aguardando Aprovação',
      'AGUARDANDO_PECAS': 'Aguardando Peças',
      'AGUARDANDO_RETIRADA': 'Aguardando Retirada',
      'ORCAMENTO_APROVADO': 'Orçamento Aprovado',
      'PENDENTE': 'Pendente',
      'CONCLUIDO': 'Concluído',
      'CANCELADA': 'Cancelada',
      'ENCERRADA': 'Encerrada'
    };

    return statusMap[status.toUpperCase()] || status;
  }

  getStatusClass(status: string): string {
    if (!status) return 'status-badge status-novo';
    const statusUpper = status.toUpperCase().trim();
    return `status-badge status-${statusUpper}`;
  }

  visualizarDetalhes(osRapida: OsRapida): void {
    this.dialog.open(OsRapidaDetailDialogComponent, {
      width: '600px',
      data: { osRapida: osRapida }
    });
  }

  editarOsRapida(osRapida: OsRapida): void {
    if (osRapida.id) {
      this.router.navigate(['/os-rapida/edit', osRapida.id]);
    } else {
      this.toast.error('ID da OS não encontrado');
    }
  }

  gerarPdf(osRapida: OsRapida): void {
    if (osRapida.id) {
      const tecnicoResponsavel = sessionStorage.getItem('usuarioNome') || 'Técnico';
      const headers = new HttpHeaders({
        'TecnicoResponsavel': tecnicoResponsavel,
        'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
      });
      const options = { headers: headers, responseType: 'blob' as 'json' };

      this.http.post(`http://localhost:8080/gerarPdf/osRapida?id=${osRapida.id}`, {}, options)
        .subscribe({
          next: (response: any) => {
            const blob = new Blob([response], { type: 'application/pdf' });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = `os-rapida-${osRapida.id}.pdf`;
            link.click();
            window.URL.revokeObjectURL(url);
            this.toast.success('PDF gerado com sucesso!');
          },
          error: (error) => {
            this.toast.error('Erro ao gerar PDF: ' + error.message);
          }
        });
    } else {
      this.toast.error('ID da OS não encontrado');
    }
  }

  encerrarOsRapida(osRapida: OsRapida): void {
    if (osRapida.id && (osRapida.status !== 'ENCERRADA' && osRapida.status !== 'CANCELADA')) {
      const dialogRef = this.dialog.open(OsRapidaCloseDialogComponent, {
        width: '500px',
        data: { osRapida: osRapida, action: 'close' }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.osRapidaService.encerrarOsRapida(osRapida.id!).subscribe({
            next: () => {
              this.toast.success('OS Rápida encerrada com sucesso!');
              this.findAllOsRapidas(); // Recarrega a lista
            },
            error: (error) => {
              this.toast.error('Erro ao encerrar OS Rápida: ' + error.message);
            }
          });
        }
      });
    }
  }

  cancelarOsRapida(osRapida: OsRapida): void {
    if (osRapida.id && (osRapida.status !== 'ENCERRADA' && osRapida.status !== 'CANCELADA')) {
      const dialogRef = this.dialog.open(OsRapidaCloseDialogComponent, {
        width: '500px',
        data: { osRapida: osRapida, action: 'cancel' }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.osRapidaService.cancelarOsRapida(osRapida.id!).subscribe({
            next: () => {
              this.toast.success('OS Rápida cancelada com sucesso!');
              this.findAllOsRapidas(); // Recarrega a lista
            },
            error: (error) => {
              this.toast.error('Erro ao cancelar OS Rápida: ' + error.message);
            }
          });
        }
      });
    }
  }

}
