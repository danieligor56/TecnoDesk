import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardStats } from '../models/DashboardStats';

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

        return this.http.get<DashboardStats>("http://localhost:8080/Dashboard/stats", options);
    }
}
