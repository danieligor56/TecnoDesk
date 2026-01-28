import { Injectable } from '@angular/core';
import { Creds } from '../models/creds';
import { Usuarios } from '../models/Usuarios';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }

  criarUsuario(usuario: Usuarios): Observable<Usuarios> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }
    return this.http.post<Usuarios>(`${environment.apiUrl}/auth/register`, usuario, options);
  }

  listarUsuarios(): Observable<Usuarios[]> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    })
    const options = { headers: headers }
    return this.http.get<Usuarios[]>(`${environment.apiUrl}/auth/listar`, options);
  }
}
