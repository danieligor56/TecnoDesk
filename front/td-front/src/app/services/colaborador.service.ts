import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Colaborador } from '../models/Colaborador';
import { Form, FormGroup } from '@angular/forms';
import { HtmlTagDefinition } from '@angular/compiler';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ColaboradorService {



  constructor(private http: HttpClient) { }


  alterarColaborador(id: bigint, colaborador: Colaborador): Observable<Colaborador> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }

    return this.http.put<Colaborador>(`${environment.apiUrl}/api/v1/Colaborador/alterarColab/{id}?id=${id}`, colaborador, options)

  }

  deletarColaborador(id: bigint): Observable<Colaborador> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }
    return this.http.delete<Colaborador>(`${environment.apiUrl}/api/v1/Colaborador/deletarColaborador?id=${id}`, options)
  }

  findByID(id: bigint): Observable<Colaborador> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }


    return this.http.get<Colaborador>(`${environment.apiUrl}/api/v1/Colaborador/buscarporID?id=${id}`, options)
  }

  findAll(): Observable<Colaborador[]> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }

    return this.http.get<Colaborador[]>(`${environment.apiUrl}/api/v1/Colaborador/listarColaboradores`, options);
  }

  create(colaborador: Colaborador): Observable<Colaborador> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }
    return this.http.post<Colaborador>(`${environment.apiUrl}/api/v1/Colaborador/AdicionaNovoColaborador`, colaborador, options);
  }

  verificaUsuario(email: string) {
    return this.http.get<boolean>(`${environment.apiUrl}/auth/vericarUsuario?email=${email}`);

  }

  listarTecnicos(): Observable<Colaborador[]> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }
    return this.http.get<Colaborador[]>(`${environment.apiUrl}/api/v1/Colaborador/listarTecnicos`, options);

  }



}
