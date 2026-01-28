import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OsRapida } from '../models/OsRapida';
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OsRapidaService {

  constructor(
    private http: HttpClient,
    private toast: ToastrService
  ) { }

  criarOsRapida(osRapida: OsRapida): Observable<OsRapida> {
    const tecnicoResponsavel = sessionStorage.getItem('usuarioNome') || 'Técnico';
    const headers = new HttpHeaders({
      'TecnicoResponsavel': tecnicoResponsavel,
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    });
    const options = { headers: headers };

    return this.http.post<OsRapida>(`${environment.apiUrl}/OsRapida/criar`, osRapida, options);
  }

  listarOsRapidas(): Observable<OsRapida[]> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    });
    const options = { headers: headers };

    return this.http.get<OsRapida[]>(`${environment.apiUrl}/OsRapida/listar`, options);
  }

  listarOsRapidasPorTecnico(): Observable<OsRapida[]> {
    const tecnicoResponsavel = sessionStorage.getItem('usuarioNome') || 'Técnico';
    const headers = new HttpHeaders({
      'TecnicoResponsavel': tecnicoResponsavel,
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    });
    const options = { headers: headers };

    return this.http.get<OsRapida[]>(`${environment.apiUrl}/OsRapida/listarPorTecnico`, options);
  }

  buscarOsRapidaPorId(id: number): Observable<OsRapida> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    });
    const options = { headers: headers };

    return this.http.get<OsRapida>(`${environment.apiUrl}/OsRapida/${id}`, options);
  }

  atualizarOsRapida(id: number, osRapida: OsRapida): Observable<OsRapida> {
    const tecnicoResponsavel = sessionStorage.getItem('usuarioNome') || 'Técnico';
    const headers = new HttpHeaders({
      'TecnicoResponsavel': tecnicoResponsavel,
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    });
    const options = { headers: headers };

    return this.http.put<OsRapida>(`${environment.apiUrl}/OsRapida/atualizar/${id}`, osRapida, options);
  }

  encerrarOsRapida(id: number): Observable<void> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    });
    const options = { headers: headers };

    return this.http.put<void>(`${environment.apiUrl}/OsRapida/encerrar/${id}`, {}, options);
  }

  cancelarOsRapida(id: number): Observable<void> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    });
    const options = { headers: headers };

    return this.http.put<void>(`${environment.apiUrl}/OsRapida/cancelar/${id}`, {}, options);
  }

}
