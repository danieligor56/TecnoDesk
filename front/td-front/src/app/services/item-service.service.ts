import { Injectable } from '@angular/core';
import { ItemService } from '../models/ItemService';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Form, FormGroup } from '@angular/forms';
import { HtmlTagDefinition } from '@angular/compiler'

@Injectable({
  providedIn: 'root'
})
export class ItemServiceService {

  constructor(private http: HttpClient) { }

  create(itemServico:ItemService): Observable<ItemService>{
      const headers = new HttpHeaders({
        'codEmpresa':sessionStorage.getItem('CompGrpIndent')
      })
      const options = { headers: headers }
      return this.http.post<ItemService>("http://localhost:8080/api/v1/servico/adicionarServico",itemServico,options);
  }

  listServico(): Observable<ItemService[]>{
    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }
    return this.http.get<ItemService[]>("http://localhost:8080/api/v1/servico/listaServico",options)
  }

  encontrarPorId(id:string): Observable<ItemService>{
    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }
    return this.http.get<ItemService>(`http://localhost:8080/api/v1/servico/buscarServico/{id}?id=${id}`,options)
  }

  alterarServico(id:number,servico:ItemService){
    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }
      return this.http.put<ItemService>(`http://localhost:8080/api/v1/servico/alterarServico?id=${id}`,servico,options);
  }

  deletarServico(id:number){

    const headers = new HttpHeaders({
      'codEmpresa':sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.delete(`http://localhost:8080/api/v1/servico/deleteServico/{id}?id=${id}`,options)
  }







}
