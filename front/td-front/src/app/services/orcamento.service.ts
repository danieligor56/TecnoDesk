import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Orcamento } from '../models/Orcamento';
import { ItemService } from '../models/ItemService';
import { OrcamentoItem } from '../models/OrcamentoItem';
import { TotaisNotaDTO } from '../DTO/TotaisNotaDTO';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrcamentoService {

  constructor(
    private http: HttpClient

  ) { }

  buscarPorId(numOs: number): Observable<Orcamento> {
    debugger
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }
    return this.http.get<Orcamento>(`${environment.apiUrl}/api/v1/orcamento/buscarOcamento?id=${numOs}`, options);

  }

  inserirItem(codOrcamento: number, servicoItem: OrcamentoItem): Observable<OrcamentoItem> {
    debugger;
    const codEmpresa = sessionStorage.getItem('CompGrpIndent');

    const headers = new HttpHeaders({
      'codEmpresa': codEmpresa || ''  // Garante que não seja null
    });

    const options = { headers };

    return this.http.post<OrcamentoItem>(

      `${environment.apiUrl}/api/v1/orcamento/inserirServico?orcamentoId=${codOrcamento}`,
      servicoItem,
      options
    );
  }

  listarServicosOrcamento(idOrcamento: number): Observable<OrcamentoItem[]> {
    const codEmpresa = sessionStorage.getItem('CompGrpIndent');

    const headers = new HttpHeaders({
      'codEmpresa': codEmpresa
    });

    const options = { headers };

    return this.http.get<OrcamentoItem[]>(`${environment.apiUrl}/api/v1/orcamento/listarServicosOrcamento?orcamento_id=${idOrcamento}`, options);
  }

  valorOrcamento(idOrcamento: number): Observable<TotaisNotaDTO> {
    const codEmpresa = sessionStorage.getItem('CompGrpIndent');

    const headers = new HttpHeaders({
      'codEmpresa': codEmpresa
    });

    const options = { headers };

    return this.http.get<TotaisNotaDTO>(`${environment.apiUrl}/api/v1/orcamento/valorOrcamento?orcamento_id=${idOrcamento}`, options);
  }

  atualizarDesconto(itemId: number, desconto: number): Observable<OrcamentoItem> {
    const codEmpresa = sessionStorage.getItem('CompGrpIndent');

    const headers = new HttpHeaders({
      'codEmpresa': codEmpresa
    });

    const options = { headers };

    return this.http.post<OrcamentoItem>(
      `${environment.apiUrl}/api/v1/orcamento/atualizarDesconto?itemId=${itemId}&desconto=${desconto}`,
      {},
      options
    );
  }

  removerItem(idItem: number, codigoOrcamento: number): Observable<any> {
    const codEmpresa = sessionStorage.getItem('CompGrpIndent');

    const headers = new HttpHeaders({
      'codEmpresa': codEmpresa
    });

    const options = { headers };

    return this.http.delete(
      `${environment.apiUrl}/api/v1/orcamento/exluirServico?idItemOrcamento=${idItem}&codigoOrcamento=${codigoOrcamento}`,
      options
    );
  }


}
