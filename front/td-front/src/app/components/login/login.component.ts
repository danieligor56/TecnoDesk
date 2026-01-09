import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Creds } from 'src/app/models/creds';
import { AuthService } from 'src/app/services/auth.service';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [
    trigger('fadeAnimation', [
      state('login', style({
        opacity: 1,
        transform: 'translateX(0)'
      })),
      state('cadastro', style({
        opacity: 1,
        transform: 'translateX(0)'
      })),
      transition('login => cadastro', [
        style({ opacity: 0, transform: 'translateX(20px)' }),
        animate('300ms ease-out')
      ]),
      transition('cadastro => login', [
        style({ opacity: 0, transform: 'translateX(-20px)' }),
        animate('300ms ease-out')
      ])
    ])
  ]
})

export class LoginComponent {

  mostrarCadastro = false;

  email = new FormControl('', [Validators.required, Validators.email]);
  pass = new FormControl('', [Validators.required]);

  cred = {
    email: '',
    pass: ''
  };

  dados: any;

  validaInput(): boolean {
    return this.email.valid && this.pass.valid;
  }

  constructor(
    private toast: ToastrService,
    private service: AuthService,
    private router: Router
  ) { }

  logar(): void {
    if (this.validaInput()) {
      localStorage.clear();
      this.service.anthenticate(this.cred).subscribe(resposta => {
        this.service.succesLogin(resposta.token);
        sessionStorage.setItem('usuarioNome', resposta.nomeUsuario)
        sessionStorage.setItem('CompGrpIndent',resposta.CompGrpIndent)
        this.router.navigate(['']);
        // this.service.getCodEmpresa(this.cred.email).subscribe(
        //   (key) => {
        //     sessionStorage.setItem('CompGrpIndent', JSON.stringify(key).replace(/["]/g, ''));
        //   }
        // );
      },
        () => {
          this.toast.error('Usuário e/ou senha inválidos');
        });
    }
  }

  mostrarTelaCadastro(): void {
    this.mostrarCadastro = true;
  }

  voltarParaLogin(): void {
    this.mostrarCadastro = false;
  }
}
