import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiPlacaResponseDTO } from '../DTO/ApiPlacaResponseDTO ';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor(private http: HttpClient) { }

  buscaCep(cep: String): Observable<any> {
    return this.http.get(`https://viacep.com.br/ws/${cep}/json/`);
  }

  pegarDadosCarro(placa: String): Observable<ApiPlacaResponseDTO> {
    return this.http.get<ApiPlacaResponseDTO>(`${environment.apiUrl}/api/v1/veiculos/getDadosCarro?placa=${placa}`)
  }




}
