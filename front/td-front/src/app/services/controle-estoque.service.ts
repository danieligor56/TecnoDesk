import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ControleEstoque } from '../models/ControleEstoque';
import { MovimentacaoEstoque } from '../models/MovimentacaoEstoque';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ControleEstoqueService {

  constructor(private http: HttpClient) { }

  listarControleEstoque(): Observable<ControleEstoque[]> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers };
    return this.http.get<ControleEstoque[]>(`${environment.apiUrl}/api/v1/estoque/listar`, options);
  }

  listarProdutosEstoqueBaixo(): Observable<ControleEstoque[]> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers };
    return this.http.get<ControleEstoque[]>(`${environment.apiUrl}/api/v1/estoque/estoqueBaixo`, options);
  }

  buscarControleEstoquePorId(id: number): Observable<ControleEstoque> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers };
    return this.http.get<ControleEstoque>(`${environment.apiUrl}/api/v1/estoque/${id}`, options);
  }

  criarOuAtualizarControleEstoque(controleEstoque: any): Observable<ControleEstoque> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers };
    return this.http.post<ControleEstoque>(`${environment.apiUrl}/api/v1/estoque/criarOuAtualizar`, controleEstoque, options);
  }

  realizarMovimentacao(movimentacao: any): Observable<MovimentacaoEstoque> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers };
    return this.http.post<MovimentacaoEstoque>(`${environment.apiUrl}/api/v1/estoque/movimentacao`, movimentacao, options);
  }

  listarMovimentacoes(): Observable<MovimentacaoEstoque[]> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers };
    return this.http.get<MovimentacaoEstoque[]>(`${environment.apiUrl}/api/v1/estoque/movimentacoes`, options);
  }

  listarMovimentacoesPorProduto(produtoId: number): Observable<MovimentacaoEstoque[]> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers };
    return this.http.get<MovimentacaoEstoque[]>(`${environment.apiUrl}/api/v1/estoque/movimentacoes/produto/${produtoId}`, options);
  }

}

