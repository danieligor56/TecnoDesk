import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Colaborador } from 'src/app/models/Colaborador';
import { Os_entrada } from 'src/app/models/Os-entrada';
import { ColaboradorService } from 'src/app/services/colaborador.service';
import { OsService } from 'src/app/services/os.service';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-os-manager',
  templateUrl: './os-manager.component.html',
  styleUrls: ['./os-manager.component.css']
})
export class OsManagerComponent implements OnInit {
  [x: string]: any;
nomeCliente:string = '';
Os:Os_entrada;
id:string;
colaboradores:Colaborador[] = [];
osCreateForm:FormGroup;
colaborador1:Colaborador;
  
constructor(
    private osService: OsService,
    private route: ActivatedRoute,
    private colaboradorService:ColaboradorService
    

  ) { }

  ngOnInit(): void {
    debugger;
    this.id = this.route.snapshot.paramMap.get('id');

    this.mapearDadosOS(this.id)
    this.dropdownColaborador();
    console.log(this.colaboradores)

  }

  mapearDadosOS(id:string){
    
     return this.osService.findOsByNumOs(Number(id)).subscribe(
      response => {
        this.Os = response;
      })

  }

  dropdownColaborador(){
    debugger;
    this.colaboradorService.listarTecnicos().subscribe(
      (response) => {
        this.colaboradores = response
    }),

    (error) => {
      console.error("Não foi possível carregar os colaboradores.")
    }

  };

  onColaboradorChange(colaboradorId: number) {
    debugger;
    this.osCreateForm.patchValue({
      colaborador: { id: colaboradorId }
    });
  }


  
  
}
