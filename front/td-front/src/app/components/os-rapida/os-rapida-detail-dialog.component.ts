import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { OsRapida } from 'src/app/models/OsRapida';

@Component({
  selector: 'app-os-rapida-detail-dialog',
  templateUrl: './os-rapida-detail-dialog.component.html',
  styleUrls: ['./os-rapida-detail-dialog.component.css']
})
export class OsRapidaDetailDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<OsRapidaDetailDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { osRapida: OsRapida }
  ) {}

  onClose(): void {
    this.dialogRef.close();
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
}
