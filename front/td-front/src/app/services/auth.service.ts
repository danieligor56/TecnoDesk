import { Injectable } from '@angular/core';
import { Creds } from '../models/creds';
import { HttpClient } from '@angular/common/http';
import { API_COONFIG } from '../config/api.config';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  jwtService: JwtHelperService = new JwtHelperService();

  constructor(private http:HttpClient) { }
  
  anthenticate(cred:Creds){
    return this.http.post("http://localhost:8080/auth/login",cred,{
      observe:'response',
      responseType:'text'
    }) 
  }

  succesLogin(authToken:string) {
    localStorage.setItem('token',authToken);
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
  }

}