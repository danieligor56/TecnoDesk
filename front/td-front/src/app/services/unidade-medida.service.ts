import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UnidadeMedida {
  id?: number;
  nome: string;
  sigla: string;
  empresaId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class UnidadeMedidaService {

  private readonly API = 'http://localhost:8080/api/v1/unidades-medida';

  constructor(private http: HttpClient) { }

  private getHeaders() {
    return new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    });
  }

  listar(): Observable<UnidadeMedida[]> {
    return this.http.get<UnidadeMedida[]>(this.API, { headers: this.getHeaders() });
  }

  criar(unidade: UnidadeMedida): Observable<UnidadeMedida> {
    return this.http.post<UnidadeMedida>(this.API, unidade, { headers: this.getHeaders() });
  }

  atualizar(id: number, unidade: UnidadeMedida): Observable<UnidadeMedida> {
    return this.http.put<UnidadeMedida>(`${this.API}/${id}`, unidade, { headers: this.getHeaders() });
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`, { headers: this.getHeaders() });
  }
}
