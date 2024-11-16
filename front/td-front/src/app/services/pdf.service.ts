import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Os_entrada } from '../models/Os-entrada';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PdfService {

  constructor(
    private http:HttpClient
  ) { }

  gerarPdfOsEntrada(os:Os_entrada): Observable<Blob>{
  debugger;
    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers, responseType: 'blob' as 'json' }; 
    
    return this.http.post<Blob>("http://localhost:8080/gerarPdf/osEntrada",os,options);
  }  

}
