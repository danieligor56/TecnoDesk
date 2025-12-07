import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OrcamentoItem } from 'src/app/models/OrcamentoItem';

@Component({
  selector: 'app-discount-dialog',
  templateUrl: './discount-dialog.component.html',
  styleUrls: ['./discount-dialog.component.css']
})
export class DiscountDialogComponent {
  discountType: 'reais' | 'percent' = 'reais';
  discountValue: number = 0;
  descontoServico: number = 0;

  constructor(
    public dialogRef: MatDialogRef<DiscountDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { item: OrcamentoItem }
  ) {
    this.descontoServico = data.item.descontoServico || 0;
    this.calculateDiscount();
  }

  onDiscountChange(): void {
    this.calculateDiscount();
  }

  private calculateDiscount(): void {
    if (this.discountType === 'percent') {
      const valorBase = this.data.item.valorUnidadeAvulso || this.data.item.valorHoraAvulso || 0;
      this.descontoServico = (valorBase * this.discountValue) / 100;
    } else {
      this.descontoServico = this.discountValue;
    }
  }

  applyDiscount(): void {
    this.calculateDiscount();
    const valorBase = this.data.item.valorUnidadeAvulso || this.data.item.valorHoraAvulso || 0;
    const valorTotal = valorBase - this.descontoServico;

    const result = {
      descontoServico: this.descontoServico,
      valorTotal: valorTotal > 0 ? valorTotal : 0 // prevent negative values
    };

    this.dialogRef.close(result);
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
