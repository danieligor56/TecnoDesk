import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ItemServiceService } from 'src/app/services/item-service.service';
import { OrcamentoService } from 'src/app/services/orcamento.service';


@Component({
  selector: 'app-item-service-create-avulso',
  templateUrl: './item-service-create-avulso.component.html',
  styleUrls: ['./item-service-create-avulso.component.css']
})
export class ItemServiceCreateAvulsoComponent implements OnInit {
  isService:boolean = false;
  itemOrcamentoForm:FormGroup;
  codOrcamento:number;

  constructor(
    private dialogRef: MatDialogRef<ItemServiceCreateAvulsoComponent>,
    private fb: FormBuilder,
    private  orcamentoService:OrcamentoService,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private itemService:ItemServiceService,

       

  ) { }

  ngOnInit(): void {

    this.itemOrcamentoForm = this.fb.group({
      empresa:[],
      codOrcamento:[],
      codigoItem:[],
      nomeServicoAvulso:[],
      descricaoServicoAvulso:[],
      valorUnidadeAvulso:[],
      valorHoraAvulso:[],
      isAvulso:[]
    })

  }

  inserirServicoAvulso(){
    
      this.orcamentoService.buscarPorId(this.data.id).subscribe(orcamento => {
      this.codOrcamento = orcamento.id
      debugger;
      this.orcamentoService.inserirItem(this.codOrcamento,this.itemOrcamentoForm.value)
      this.closeDialog() 
 
    });
  }

  closeDialog(){
    this.dialogRef.close();
  }
  

}
