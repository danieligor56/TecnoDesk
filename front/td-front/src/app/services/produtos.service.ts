import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produtos } from '../models/Produtos';

@Injectable({
  providedIn: 'root'
})
export class ProdutosService {

  constructor( private http: HttpClient) { }

  listarProdutos(): Observable<Produtos[]>{
      const headers = new HttpHeaders({
        'codEmpresa':sessionStorage.getItem('CompGrpIndent')
      })

      const options = { headers: headers }
      return this.http.get<Produtos[]>("http://localhost:8080/api/v1/produtos/listarProdutos?codEmpresa=://localhost:8080/api/v1/servico/listaServico",options)
    }
}
