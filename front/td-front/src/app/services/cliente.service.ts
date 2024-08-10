import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../models/Cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private http:HttpClient) { }

  findAll(): Observable<Cliente[]> {
    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }

    return this.http.get<Cliente[]>("http://localhost:8080/api/v1/cliente/listaClientes",options);
  }


}
