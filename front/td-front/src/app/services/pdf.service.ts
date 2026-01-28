import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Os_entrada } from '../models/Os-entrada';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PdfService {

  constructor(
    private http: HttpClient
  ) { }

  gerarPdfOsEntrada(os: Os_entrada): Observable<Blob> {
    debugger;
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers, responseType: 'blob' as 'json' };

    return this.http.post<Blob>(`${environment.apiUrl}/gerarPdf/osEntrada`, os, options);
  }

  gerarPdfOrcamento(numOS: number): Observable<Blob> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    });
    const params = new HttpParams().set('numOS', numOS.toString());
    const options = {
      headers: headers,
      responseType: 'blob' as 'json',
      params: params
    };

    return this.http.post<Blob>(`${environment.apiUrl}/gerarPdf/orcamento`, null, options);
  }

}
