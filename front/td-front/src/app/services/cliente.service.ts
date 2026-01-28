import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../models/Cliente';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Cliente[]> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }

    return this.http.get<Cliente[]>(`${environment.apiUrl}/api/v1/cliente/listaClientes`, options);
  }

  create(cliente: Cliente): Observable<Cliente> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }
    return this.http.post<Cliente>(`${environment.apiUrl}/api/v1/cliente/adicionaNovoCliente`, cliente, options);
  }

  findByID(id: bigint): Observable<Cliente> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }


    return this.http.get<Cliente>(`${environment.apiUrl}/api/v1/cliente/buscarCliente/{id}?id=${id}`, options)
  }

  deletarCliente(id: bigint): Observable<Cliente> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }
    return this.http.delete<Cliente>(`${environment.apiUrl}/api/v1/cliente/deleteCliente/{id}?id=${id}`, options)
  }

  alterarCliente(id: bigint, cliente: Cliente): Observable<Cliente> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }

    return this.http.put<Cliente>(`${environment.apiUrl}/api/v1/cliente/alterarCliente?id=${id}`, cliente, options)

  }

  encontrarClientePorDocumento(doc: string): Observable<Cliente> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.get<Cliente>(`${environment.apiUrl}/api/v1/cliente/buscarpordoc/{Doc}?Doc=${doc}`, options)
  }


}
