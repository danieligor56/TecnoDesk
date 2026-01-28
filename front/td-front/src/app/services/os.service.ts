import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Os_entrada } from '../models/Os-entrada';
import { Os_Mecanica } from '../models/Os-mecanica';
import { Observable } from 'rxjs';
import { TecnicoEPrioridadeDTO } from '../DTO/TecnicoEPrioridadeDTO';
import { ToastrService } from 'ngx-toastr';
import { laudoTecnicoDTO } from '../DTO/LaudoTecnicoDTO';
import { HistoricoOS } from '../models/HistoricoOS';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OsService {

  constructor(
    private http: HttpClient,
    private toast: ToastrService
  ) { }

  createOsEntrada(os: Os_entrada): Observable<Os_entrada> {
    debugger;
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.post<Os_entrada>(`${environment.apiUrl}/Os/criarNovaOS`, os, options);

  }

  findAllOs(): Observable<Os_entrada[]> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.get<Os_entrada[]>(`${environment.apiUrl}/Os/listarOS`, options);

  }

  findOsByNumOs(numOs: number): Observable<Os_entrada> {

    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.get<Os_entrada>(`${environment.apiUrl}/Os/numOs?numOS=${numOs}`, options);;

  }

  alterarTecnicoEPrioridade(dto: TecnicoEPrioridadeDTO): Observable<any> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.put(`${environment.apiUrl}/Os/alterarTecnicEprioridade`, dto, options);
  }

  alterarStatusOS(numOs: number, stsOS: number): Observable<any> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.put(
      `${environment.apiUrl}/Os/alterarStatusOs?numOs=${numOs}&stsOS=${stsOS}`, {}, options);

  }

  alterarDiagnosticoTecnico(dto: laudoTecnicoDTO) {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.post(`${environment.apiUrl}/Os/salvarDiagnostico`, dto, options);
  }

  createOsMecanica(os: Os_Mecanica): Observable<Os_Mecanica> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.post<Os_Mecanica>(`${environment.apiUrl}/Os/criarNovaOSMecanica`, os, options);
  }

  getHistorico(osId: number): Observable<HistoricoOS[]> {
    const headers = new HttpHeaders({
      'codEmpresa': sessionStorage.getItem('CompGrpIndent')
    })
    const options = { headers: headers }

    return this.http.get<HistoricoOS[]>(`${environment.apiUrl}/Os/${osId}/historico`, options);
  }

}
