import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { OsRapida } from 'src/app/models/OsRapida';
import { OsRapidaService } from 'src/app/services/os-rapida.service';
import { OsRapidaDetailDialogComponent } from './os-rapida-detail-dialog.component';

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
    private router: Router
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
    // Por enquanto, redirecionar para a página de criação
    // Em uma implementação futura, poderia passar o ID para edição
    alert('Funcionalidade de edição será implementada em breve.\n\nPara editar, crie uma nova OS com os dados atualizados.');
  }

}
