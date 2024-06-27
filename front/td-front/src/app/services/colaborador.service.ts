import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Colaborador } from '../models/Colaborador';
import { Form, FormGroup } from '@angular/forms';
import { HtmlTagDefinition } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class ColaboradorService {

  

  constructor(private http: HttpClient) { }
  
  findAll(): Observable<Colaborador[]> {
    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })

    const options = { headers: headers }

    return this.http.get<Colaborador[]>("http://localhost:8080/api/v1/Colaborador/listarColaboradores",options);
  }
  
  create(colaborador:Colaborador): Observable<Colaborador>{
    
    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })
    
    const options = { headers: headers }
    return this.http.post<Colaborador>("http://localhost:8080/api/v1/Colaborador/AdicionaNovoColaborador",colaborador,options);
  }
}
