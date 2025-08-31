import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Orcamento } from '../models/Orcamento';
import { ItemService } from '../models/ItemService';
import { OrcamentoItem } from '../models/OrcamentoItem';

@Injectable({
  providedIn: 'root'
})
export class OrcamentoService {

  constructor(
    private http:HttpClient

  ) { }

  buscarPorId(numOs: number): Observable<Orcamento>{
    debugger
    const headers = new HttpHeaders({
          'codEmpresa':sessionStorage.getItem('CompGrpIndent')
        })

    const options = { headers: headers }
    return this.http.get<Orcamento>(`http://localhost:8080/api/v1/orcamento/buscarOcamento?id=${numOs}`,options);
  
  }

  inserirItem(codOrcamento: number, servicoItem: OrcamentoItem): Observable<OrcamentoItem> {
    debugger;
    const codEmpresa = sessionStorage.getItem('CompGrpIndent');
  
    const headers = new HttpHeaders({
      'codEmpresa': codEmpresa || ''  // Garante que não seja null
    });
  
    const options = { headers };
  
    return this.http.post<OrcamentoItem>(
    
      `http://localhost:8080/api/v1/orcamento/inserirServico?orcamentoId=${codOrcamento}`,
      servicoItem,
      options
    );
  }

  listarServicosOrcamento(idOrcamento:number): Observable<OrcamentoItem[]>{
    const codEmpresa = sessionStorage.getItem('CompGrpIndent');
  
    const headers = new HttpHeaders({
      'codEmpresa': codEmpresa
    });
  
    const options = { headers };

    return this.http.get<OrcamentoItem[]>(`http://localhost:8080/api/v1/orcamento/listarServicosOrcamento?orcamento_id=${idOrcamento}`,options);
  }

  valorOrcamento(idOrcamento:number): Observable<number>{
    const codEmpresa = sessionStorage.getItem('CompGrpIndent');
  
    const headers = new HttpHeaders({
      'codEmpresa': codEmpresa
    });
  
    const options = { headers };

    return this.http.get<number>(`http://localhost:8080/api/v1/orcamento/valorOrcamento?orcamento_id=${idOrcamento}`,options);
  }


}
