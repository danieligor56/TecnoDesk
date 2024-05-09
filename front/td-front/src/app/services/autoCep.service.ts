import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CepService {

  constructor(private http:HttpClient) { }

  buscaCep(cep:String): Observable<any>{
    return this.http.get(`https://viacep.com.br/ws/${cep}/json/`);
  }




}
