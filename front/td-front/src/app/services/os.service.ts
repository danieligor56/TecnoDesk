import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Os_entrada } from '../models/Os-entrada';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OsService {

  constructor(
    private http:HttpClient
  ) { }

  createOsEntrada(os:Os_entrada): Observable<Os_entrada>{
  debugger;   
    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.post<Os_entrada>("http://localhost:8080/Os/criarNovaOS",os,options);
    
    }

    findAllOs(): Observable<Os_entrada[]>{
     
      const headers = new HttpHeaders({
        'codEmpresa':sessionStorage.getItem('CompGrpIndent')
      })
      const options = { headers: headers }

      return this.http.get<Os_entrada[]>("http://localhost:8080/Os/listarOS",options);
    
    }

    findOsByNumOs(numOs:bigint): Observable<Os_entrada>{
     
      const headers = new HttpHeaders({
        'codEmpresa':sessionStorage.getItem('CompGrpIndent')
      })
      const options = { headers: headers }

      return this.http.get<Os_entrada>(`http://localhost:8080/Os/numOs?numOS=${numOs}`,options);
    
    }
  
  

}
