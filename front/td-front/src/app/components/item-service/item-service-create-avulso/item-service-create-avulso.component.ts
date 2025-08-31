import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ItemServiceService } from 'src/app/services/item-service.service';
import { OrcamentoService } from 'src/app/services/orcamento.service';
import { ItemServiceCobrarhoraComponent } from '../item-service-cobrarhora/item-service-cobrarhora.component';
import { ItemService } from 'src/app/models/ItemService';
import { OrcamentoItem } from 'src/app/models/OrcamentoItem';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-item-service-create-avulso',
  templateUrl: './item-service-create-avulso.component.html',
  styleUrls: ['./item-service-create-avulso.component.css']
})
export class ItemServiceCreateAvulsoComponent implements OnInit {
  isService:boolean = false;
  itemOrcamentoForm:FormGroup;
  codOrcamento:number;
  radius: number = 0;
  validarHora: boolean = false
  vTotal: number = 0;
  novoServico:ItemService;
  novoOrcamentoItem:OrcamentoItem;

  constructor(
    private dialogRef: MatDialogRef<ItemServiceCreateAvulsoComponent>,
    private fb: FormBuilder,
    private  orcamentoService:OrcamentoService,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private dialog: MatDialog,
    private itemServicoService:ItemServiceService,
    private toast:ToastrService,    
  ) { }

  ngOnInit(): void {

    this.itemOrcamentoForm = this.fb.group({
      empresa:[],
      codOrcamento:[],
      codigoItem:[],
      nomeServicoAvulso:[],
      descricaoServicoAvulso:[null,Validators.required],
      valorUnidadeAvulso:[null,Validators.required],
      valorHoraAvulso:[this.vTotal],
      isAvulso:[]
    })

  }

  calcValorPorHora() {
    debugger;
    const horas = Number(this.radius) || 0;
    const valor = Number(this.itemOrcamentoForm.get('valorUnidadeAvulso').value) || 0;
  
    this.vTotal = valor * horas;
  }

  validaCobrarPorHora(){
    if(this.radius <= 0){
      this.validarHora = true;
    } else {
      this.validarHora = false;
    }      
  }

  cobrarPorHora(){
    if(this.radius > 0){
     const numbOs = Number(this.data.id);
      this.orcamentoService.buscarPorId(numbOs).subscribe(orcamento => {
        this.codOrcamento = orcamento.id
      })
      
      try {
        debugger;
          this.calcValorPorHora();
          // criar primeiro novo Servico
          if(this.isService){
            this.novoServico = {
              nomeServico: this.itemOrcamentoForm.get('nomeServicoAvulso').value,
              descricaoServico:this.itemOrcamentoForm.get('descricaoServicoAvulso').value,
              valorServicoHora:this.itemOrcamentoForm.get('valorUnidadeAvulso').value,         
              valorServicoUnidade:this.itemOrcamentoForm.get('valorUnidadeAvulso').value
            }
              this.itemServicoService.create(this.novoServico).subscribe(response => {
                  if(response.id != null){
                    this.toast.success("Servico cadastrado");
                  //cadastra o servico no Orcamento
                  this.novoOrcamentoItem = {                          
                    codigoItem:Number(response.id),
                    nomeServicoAvulso:response.nomeServico,
                    descricaoServicoAvulso:response.descricaoServico,
                    valorUnidadeAvulso:0,
                    valorHoraAvulso:this.vTotal,
                    isAvulso:false
                      }
                      debugger;
                  this.orcamentoService.inserirItem(this.codOrcamento,this.novoOrcamentoItem)
                  this.dialogRef.close(true);
                                      
                }
                
                error: (error) => {
                  this.toast.error("Erro durante a criação do serviço, contate o suporte.");
                  console.log(error) 
               }         
              });

              
          }
          else {
            this.novoOrcamentoItem = {
              descricaoServicoAvulso:this.itemOrcamentoForm.get('valorUnidadeAvulso').value,
              valorUnidadeAvulso:0,
              valorHoraAvulso:this.vTotal,
              isAvulso:true            
            }
              this.orcamentoService.inserirItem(this.codOrcamento,this.novoOrcamentoItem)
              this.dialogRef.close(true);
          }
         
      } catch (error) {
        this.toast.error("Falha na criação do serviço")
        console.log(error);
      }
    }
  }

  inserirServicoAvulso(){
    debugger;
      const numbOs = Number(this.data.id);
      if(this.itemOrcamentoForm.get('valorUnidadeAvulso').valid && this.itemOrcamentoForm.get('descricaoServicoAvulso').valid){
        this.orcamentoService.buscarPorId(numbOs).subscribe(orcamento => {
          this.codOrcamento = orcamento.id
          debugger;
          this.orcamentoService.inserirItem(this.codOrcamento,this.itemOrcamentoForm.value)
          this.dialogRef.close(true);
     
        });

      }
      this.itemOrcamentoForm.get('valorUnidadeAvulso').markAsTouched();

      
  }

  closeDialog(){
    this.dialogRef.close();
  }
  

}
