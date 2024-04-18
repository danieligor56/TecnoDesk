import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Colaborador } from '../models/Colaborador';

@Injectable({
  providedIn: 'root'
})
export class ColaboradorService {

  constructor(private http: HttpClient) { }
  findAll(): Observable<Colaborador[]> {
    return this.http.get<Colaborador[]>("http://localhost:8080/api/v1/Colaborador/listarColaboradores");
  }
}
