import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CategoriaProduto {
    id?: number;
    nome: string;
    empresaId?: number;
}

@Injectable({
    providedIn: 'root'
})
export class CategoriaProdutoService {

    private readonly API = 'http://localhost:8080/api/v1/categorias-produto';

    constructor(private http: HttpClient) { }

    private getHeaders() {
        return new HttpHeaders({
            'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
        });
    }

    listar(): Observable<CategoriaProduto[]> {
        return this.http.get<CategoriaProduto[]>(this.API, { headers: this.getHeaders() });
    }

    criar(categoria: CategoriaProduto): Observable<CategoriaProduto> {
        return this.http.post<CategoriaProduto>(this.API, categoria, { headers: this.getHeaders() });
    }

    atualizar(id: number, categoria: CategoriaProduto): Observable<CategoriaProduto> {
        return this.http.put<CategoriaProduto>(`${this.API}/${id}`, categoria, { headers: this.getHeaders() });
    }

    excluir(id: number): Observable<void> {
        return this.http.delete<void>(`${this.API}/${id}`, { headers: this.getHeaders() });
    }
}
