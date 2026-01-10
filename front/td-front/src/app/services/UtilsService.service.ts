import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiPlacaResponseDTO } from '../DTO/ApiPlacaResponseDTO ';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(private http:HttpClient) { }

  buscaCep(cep:String): Observable<any>{
    return this.http.get(`https://viacep.com.br/ws/${cep}/json/`);
  }

  pegarDadosCarro(placa:String): Observable<ApiPlacaResponseDTO>{
    return this.http.get<ApiPlacaResponseDTO>(`http://localhost:8080/api/v1/veiculos/getDadosCarro?placa=${placa}`)
  }




}
