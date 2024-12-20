import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Creds } from 'src/app/models/creds';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  cred:Creds={
    email:'',
    pass:''
  }

  dados:any;

  email = new FormControl(null,Validators.email)
  pass = new FormControl(null,Validators.minLength(3))

  validaInput():boolean {
     return this.email.valid && this.pass.valid
    }

  constructor(
    private toast: ToastrService,
    private service: AuthService,
    private router: Router
    ) { }

    ngOnInit(): void {
      
    }
    
  logar(){

    localStorage.clear();
   
    this.service.anthenticate(this.cred).subscribe(resposta => {
      this.service.succesLogin(resposta.body.substring(10).replace(/["}]/g, ''));
        this.router.navigate(['']);
          this.service.getCodEmpresa (this.cred.email).subscribe(
            (key) => {
            sessionStorage.setItem('CompGrpIndent',JSON.stringify(key).replace(/["]/g,''))
          })
            
          
          
        
          
      },

      
          

  
      ()=> {
      this.toast.error('Usuário e/ou senha inválidos');
    } );
  }

 
}
