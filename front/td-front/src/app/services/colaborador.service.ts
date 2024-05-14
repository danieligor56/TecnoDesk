import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Colaborador } from '../models/Colaborador';
import { Form, FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ColaboradorService {

  constructor(private http: HttpClient) { }
  findAll(): Observable<Colaborador[]> {
    return this.http.get<Colaborador[]>("http://localhost:8080/api/v1/Colaborador/listarColaboradores");
  }

  create(colaborador:Colaborador): Observable<Colaborador>{
    return this.http.post<Colaborador>("http://localhost:8080/api/v1/Colaborador/AdicionaNovoColaborador",colaborador);
  }
}
