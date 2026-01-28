import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produtos } from '../models/Produtos';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProdutosService {

  constructor(private http: HttpClient) { }

  listarProdutos(): Observable<Produtos[]> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    })
    const options = { headers: headers }
    return this.http.get<Produtos[]>(`${environment.apiUrl}/api/v1/produtos/listarProdutos`, options)
  }

  encontrarPorId(id: any): Observable<Produtos> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    })
    const options = { headers: headers }
    return this.http.get<Produtos>(`${environment.apiUrl}/api/v1/produtos/buscarProduto/${id}`, options)
  }

  criarNovoProduto(produtoDto: any): Observable<Produtos> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    })
    const options = { headers: headers }
    return this.http.post<Produtos>(`${environment.apiUrl}/api/v1/produtos/criarProdutos`, produtoDto, options)
  }

  alterarProduto(id: number, produtoDto: any): Observable<Produtos> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    })
    const options = { headers: headers }
    return this.http.put<Produtos>(`${environment.apiUrl}/api/v1/produtos/alterarProduto/${id}`, produtoDto, options)
  }

  deletarProduto(id: any): Observable<Produtos> {
    const headers = new HttpHeaders({
      'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
    })
    const options = { headers: headers }
    return this.http.delete<Produtos>(`${environment.apiUrl}/api/v1/produtos/deletarProduto/${id}`, options)
  }





}
