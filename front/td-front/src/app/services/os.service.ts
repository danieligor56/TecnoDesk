import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Os_entrada } from '../models/Os-entrada';
import { Observable } from 'rxjs';
import { TecnicoEPrioridadeDTO } from '../DTO/TecnicoEPrioridadeDTO';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class OsService {

  constructor(
    private http:HttpClient,
     private toast: ToastrService
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

    findOsByNumOs(numOs:number): Observable<Os_entrada>{
     
      const headers = new HttpHeaders({
        'codEmpresa':sessionStorage.getItem('CompGrpIndent')
      })
      const options = { headers: headers }

      return this.http.get<Os_entrada>(`http://localhost:8080/Os/numOs?numOS=${numOs}`,options);
    
    }

    alterarTecnicoEPrioridade(dto: TecnicoEPrioridadeDTO){
    debugger;
      const headers = new HttpHeaders({
        'codEmpresa':sessionStorage.getItem('CompGrpIndent')
      })
      const options = { headers: headers }

      return this.http.put("http://localhost:8080/Os/alterarTecnicEprioridade",dto,options).subscribe({
        next: ()=> {
          this.toast.success("OS alterada com sucesso.")
        },
        error: (err) => {
          this.toast.error("Falha ao atualizar a OS, contate o suporte"+ err)
        } 
      })
    }
  
  

}
