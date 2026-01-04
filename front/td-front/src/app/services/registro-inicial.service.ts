import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmpresaUsuarioDTO } from '../models/EmpresaUsuarioDTO';
import { API_COONFIG } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class RegistroInicialService {

  private baseUrl = `${API_COONFIG.baseUrl}/api/v1/primeiroPasso`;

  constructor(private http: HttpClient) { }

  registro(dto: EmpresaUsuarioDTO): Observable<any> {
    return this.http.post(`${this.baseUrl}`, dto);
  }
}
