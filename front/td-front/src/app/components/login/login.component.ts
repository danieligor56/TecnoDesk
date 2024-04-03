import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Creds } from 'src/app/models/creds';

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

  email = new FormControl(null,Validators.email)
  pass = new FormControl(null,Validators.minLength(3))

  validaInput():boolean {
    if(this.email.valid && this.pass.valid){
      return true;
    }else return false;
   }

  constructor(private toast: ToastrService) { }

  logar(){
    this.toast.error('Usuário ou senha invalidos','login');
  }
  
  
  ngOnInit(): void {
  }

}
