import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OsRapida } from '../models/OsRapida';
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

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
      'TecnicoResponsavel': tecnicoResponsavel
    });
    const options = { headers: headers };

    return this.http.post<OsRapida>("http://localhost:8080/OsRapida/criar", osRapida, options);
  }

  listarOsRapidas(): Observable<OsRapida[]> {
    return this.http.get<OsRapida[]>("http://localhost:8080/OsRapida/listar");
  }

  listarOsRapidasPorTecnico(): Observable<OsRapida[]> {
    const tecnicoResponsavel = sessionStorage.getItem('usuarioNome') || 'Técnico';
    const headers = new HttpHeaders({
      'TecnicoResponsavel': tecnicoResponsavel
    });
    const options = { headers: headers };

    return this.http.get<OsRapida[]>("http://localhost:8080/OsRapida/listarPorTecnico", options);
  }

}
