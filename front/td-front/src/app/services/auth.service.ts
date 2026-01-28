import { Injectable } from '@angular/core';
import { Creds } from '../models/creds';
import { HttpClient } from '@angular/common/http';
import { API_COONFIG } from '../config/api.config';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { LogingResponseDTO } from '../DTO/LoginResponseDTO';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})

export class AuthService {

  jwtService: JwtHelperService = new JwtHelperService();

  private apiUrl = `${environment.apiUrl}/auth/getCodEmp`;

  constructor(private http: HttpClient) { }

  anthenticate(cred: Creds): Observable<LogingResponseDTO> {

    return this.http.post<LogingResponseDTO>(`${environment.apiUrl}/auth/login`, cred)
  }



  succesLogin(authToken: string) {
    localStorage.setItem('token', authToken);
  }

  getCodEmpresa(email: string) {
    return this.http.get<string>(`${environment.apiUrl}/auth/getCodEmp?email=${email}`, { responseType: 'text' as 'json' });
  }

  isAuth() {
    let token = localStorage.getItem('token')
    if (token != null) {
      return !this.jwtService.isTokenExpired(token);
    }
    return false;
  }

  logout() {
    localStorage.clear();
    sessionStorage.clear();
  }

}

function tap(arg0: (empresaId: any) => void): import("rxjs").OperatorFunction<number, void> {
  throw new Error('Function not implemented.');
}
