import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DashboardStats } from 'src/app/models/DashboardStats';
import { Os_entrada } from 'src/app/models/Os-entrada';
import { DashboardService } from 'src/app/services/dashboard.service';
import { OsService } from 'src/app/services/os.service';
import { MatDialog } from '@angular/material/dialog';
import { ClienteCreateComponent } from '../clientes/cliente-create/cliente-create.component';
import { CriarAlterarProdutoComponent } from '../produtos/criar-alterar-produto/criar-alterar-produto.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  stats: DashboardStats = {
    osAbertas: 0,
    emAndamento: 0,
    finalizadasHoje: 0,
    faturamentoMes: 0,
    orcamentosPendentes: 0
  };

  recentOS: Os_entrada[] = [];
  loading: boolean = true;

  constructor(
    private dashboardService: DashboardService,
    private osService: OsService,
    private router: Router,
    private dialog: MatDialog,
    private toast: ToastrService
  ) { }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData() {
    this.loading = true;

    // Load stats
    this.dashboardService.getStats().subscribe({
      next: (data) => {
        this.stats = data;
      },
      error: (err) => {
        console.error('Erro ao carregar estatísticas:', err);
        // Keep default values on error
      }
    });

    // Load recent OS
    this.osService.findAllOs().subscribe({
      next: (data) => {
        this.recentOS = data.slice(0, 3); // Get only the 3 most recent
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar OS recentes:', err);
        this.loading = false;
      }
    });
  }

  novaOS() {
    this.router.navigate(['/os/create']);
  }

  novoCliente() {
    const dialogRef = this.dialog.open(ClienteCreateComponent, {
      width: '50rem'
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadDashboardData();
      }
    });
  }

  novoProduto() {
    const dialogRef = this.dialog.open(CriarAlterarProdutoComponent, {
      width: '50rem',
      data: { eNovoProduto: true }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadDashboardData();
        this.toast.success('Produto criado com sucesso');
      }
    });
  }

  abrirOS(osSequencial: number) {
    this.router.navigate(['/manager', osSequencial]);
  }

  getStatusClass(status: string): string {
    const statusMap: { [key: string]: string } = {
      'NOVO': 'status-novo',
      'EM_ANDAMENTO': 'status-andamento',
      'AGUARDANDO_PECAS': 'status-aguardando',
      'AGUARDANDO_RESP_ORCAMENTO': 'status-aguardando',
      'ORCAMENTO_APROVADO': 'status-aprovado',
      'CONCLUIDO': 'status-concluido',
      'CANCELADA': 'status-cancelada'
    };
    return statusMap[status] || 'status-default';
  }

  getStatusLabel(status: string): string {
    const labelMap: { [key: string]: string } = {
      'NOVO': 'Nova',
      'EM_ANDAMENTO': 'Em Andamento',
      'AGUARDANDO_PECAS': 'Aguardando Peças',
      'AGUARDANDO_RESP_ORCAMENTO': 'Aguardando Orçamento',
      'ORCAMENTO_APROVADO': 'Orçamento Aprovado',
      'CONCLUIDO': 'Concluída',
      'CANCELADA': 'Cancelada'
    };
    return labelMap[status] || status;
  }
}
