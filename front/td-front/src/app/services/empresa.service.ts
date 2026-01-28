import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {

  constructor(
    private http: HttpClient
  ) { }

  checkCompanyExistsByDoc(doc: string): Observable<boolean> {
    return this.http.get<boolean>(`${environment.apiUrl}/empresa/VerificarPorDoc?documento=${doc}`)
  }

  buscarSegmentoEmpresa(): Observable<number> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }

    return this.http.get<number>(`${environment.apiUrl}/empresa/buscarSegmentoEmpresa`, options)
  }




}
