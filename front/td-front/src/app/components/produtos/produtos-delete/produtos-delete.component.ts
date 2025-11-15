import { Component, OnInit, Inject } from "@angular/core";
import { MatDialog } from '@angular/material/dialog';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Route, Router } from "@angular/router";
import { Toast, ToastrService } from "ngx-toastr";
import { ProdutosService } from "src/app/services/produtos.service";

@Component({
  selector: 'app-produtos-delete',
  templateUrl: './produtos-delete.component.html',
  styleUrls: ['./produtos-delete.component.css']
})
export class ProdutosDeleteComponent implements OnInit {

  constructor(
    private produtosService: ProdutosService,
    private dialog: MatDialog,
    private route: Router,
    private toast: ToastrService,
    public dialogRef: MatDialogRef<ProdutosDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: bigint }
  ) { }

  ngOnInit(): void {
  }

  deletarProduto(){
    this.produtosService.deletarProduto(this.data.id)
    .subscribe({
      next: resposta => {
        this.dialogRef.close(true);
        this.toast.success("Produto excluído com sucesso");
      },
      error: erro => {
        this.dialogRef.close();
        this.toast.error(erro.error.message || "Falha ao excluir o produto");
      }
    });
  }

}
