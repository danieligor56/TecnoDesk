import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {

  constructor(
    private http: HttpClient
  ) { }

  checkCompanyExistsByDoc(doc: string): Observable<boolean>{
   return this.http.get<boolean>(`http://localhost:8080/empresa/VerificarPorDoc?documento=${doc}`)
  }

  buscarSegmentoEmpresa(): Observable<number>{
     const headers = new HttpHeaders({
          'codEmpresa':sessionStorage.getItem('CompGrpIndent')
        })

        const options = { headers: headers }

     return this.http.get<number>('http://localhost:8080/empresa/buscarSegmentoEmpresa',options)
  }




}
