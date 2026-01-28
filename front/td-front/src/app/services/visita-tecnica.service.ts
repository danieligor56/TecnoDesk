import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export enum StatusVisita {
    AGENDADA = 'AGENDADA',
    EM_ANDAMENTO = 'EM_ANDAMENTO',
    FINALIZADA = 'FINALIZADA',
    CANCELADA = 'CANCELADA'
}

export enum TipoVisita {
    PREVENTIVA = 'PREVENTIVA',
    CORRETIVA = 'CORRETIVA',
    INSTALACAO = 'INSTALACAO',
    VISTORIA = 'VISTORIA',
    OUTROS = 'OUTROS'
}

export interface VisitaTecnica {
    id?: number;
    sequencial?: number;
    clienteId: number;
    clienteNome?: string;
    tecnicoId: number;
    tecnicoNome?: string;
    dataVisita: string;
    horaVisita: string;
    tipoVisita: TipoVisita;
    statusVisita: StatusVisita;
    observacoes?: string;
    relatorioTecnico?: string;
    enderecoVisita?: string;
    proximaVisitaSugerida?: string;
    empresaId?: number;
}

@Injectable({
    providedIn: 'root'
})
export class VisitaTecnicaService {

    private readonly API = `${environment.apiUrl}/api/v1/visitas-tecnicas`;

    constructor(private http: HttpClient) { }

    private getHeaders() {
        return new HttpHeaders({
            'CodEmpresa': sessionStorage.getItem('CompGrpIndent') || ''
        });
    }

    listar(): Observable<VisitaTecnica[]> {
        return this.http.get<VisitaTecnica[]>(this.API, { headers: this.getHeaders() });
    }

    criar(visita: VisitaTecnica): Observable<VisitaTecnica> {
        return this.http.post<VisitaTecnica>(this.API, visita, { headers: this.getHeaders() });
    }

    atualizar(id: number, visita: VisitaTecnica): Observable<VisitaTecnica> {
        return this.http.put<VisitaTecnica>(`${this.API}/${id}`, visita, { headers: this.getHeaders() });
    }

    alterarStatus(id: number, status: StatusVisita): Observable<VisitaTecnica> {
        return this.http.patch<VisitaTecnica>(`${this.API}/${id}/status?status=${status}`, {}, { headers: this.getHeaders() });
    }

    excluir(id: number): Observable<void> {
        return this.http.delete<void>(`${this.API}/${id}`, { headers: this.getHeaders() });
    }
}
