import { Injectable } from '@angular/core';
import { Creds } from '../models/creds';
import { HttpClient } from '@angular/common/http';
import { API_COONFIG } from '../config/api.config';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class AuthService {

  jwtService: JwtHelperService = new JwtHelperService();
  
  private apiUrl = 'http://localhost:8080/auth/getCodEmp';

  constructor(private http:HttpClient) { }
  
  anthenticate(cred:Creds){
    
    return this.http.post("http://localhost:8080/auth/login",cred,{
      observe:'response',
      responseType:'text'
      
    }) 
  }

  

  succesLogin(authToken:string){
    localStorage.setItem('token',authToken);
  }
  
  getCodEmpresa(email: string){
    return this.http.get<string>(`http://localhost:8080/auth/getCodEmp?email=${email}`,{responseType:'text' as 'json'});
    }

  isAuth(){
    let token = localStorage.getItem('token')
    if(token != null){
        return !this.jwtService.isTokenExpired(token);
    }
    return false;
  }

  logout(){
    localStorage.clear();
    sessionStorage.clear();
  }

}

function tap(arg0: (empresaId: any) => void): import("rxjs").OperatorFunction<number, void> {
  throw new Error('Function not implemented.');
}
