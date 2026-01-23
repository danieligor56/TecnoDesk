import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'app-quantity-dialog',
    templateUrl: './quantity-dialog.component.html',
    styleUrls: ['./quantity-dialog.component.css']
})
export class QuantityDialogComponent implements OnInit {
    quantidade: number = 1;
    nomeItem: string = '';

    constructor(
        public dialogRef: MatDialogRef<QuantityDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: { nomeItem: string }
    ) {
        this.nomeItem = data.nomeItem;
    }

    ngOnInit(): void { }

    confirmar(): void {
        if (this.quantidade > 0) {
            this.dialogRef.close(this.quantidade);
        }
    }

    cancelar(): void {
        this.dialogRef.close();
    }
}
