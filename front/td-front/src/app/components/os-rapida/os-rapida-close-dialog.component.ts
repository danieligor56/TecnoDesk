import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { OsRapida } from 'src/app/models/OsRapida';

@Component({
  selector: 'app-os-rapida-close-dialog',
  templateUrl: './os-rapida-close-dialog.component.html',
  styleUrls: ['./os-rapida-close-dialog.component.css']
})
export class OsRapidaCloseDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<OsRapidaCloseDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { osRapida: OsRapida; action: 'close' | 'cancel' }
  ) {}

  onConfirm(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  getTitle(): string {
    return this.data.action === 'close' ? 'Encerrar OS Rápida' : 'Cancelar OS Rápida';
  }

  getMessage(): string {
    if (this.data.action === 'close') {
      return `Ao encerrar esta OS Rápida, o valor estimado de ${this.data.osRapida.valorEstimado ? 'R$ ' + this.data.osRapida.valorEstimado : 'não informado'} será contabilizado para relatórios financeiros. Deseja prosseguir?`;
    } else {
      return 'Ao cancelar esta OS Rápida, nenhum valor será ajustado ao financeiro. A OS será marcada como cancelada. Deseja prosseguir?';
    }
  }

  getConfirmButtonText(): string {
    return this.data.action === 'close' ? 'Encerrar OS' : 'Cancelar OS';
  }

  getConfirmButtonColor(): string {
    return this.data.action === 'close' ? 'primary' : 'warn';
  }
}
