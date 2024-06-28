import { Injectable } from '@angular/core';
import { Creds } from '../models/creds';
import { Usuarios } from '../models/Usuarios';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http:HttpClient) { }

  criarUsuario(usuario:Usuarios): Observable<Usuarios>{

    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })
    
      const options = { headers: headers }
     return this.http.post<Usuarios>('http://localhost:8080/auth/register',usuario,options);
  }
}
