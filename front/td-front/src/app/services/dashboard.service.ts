import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardStats } from '../models/DashboardStats';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class DashboardService {

    constructor(private http: HttpClient) { }

    getStats(): Observable<DashboardStats> {
        const headers = new HttpHeaders({
            'codEmpresa': sessionStorage.getItem('CompGrpIndent')
        })
        const options = { headers: headers }

        return this.http.get<DashboardStats>(`${environment.apiUrl}/Dashboard/stats`, options);
    }
}
