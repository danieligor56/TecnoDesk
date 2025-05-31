import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./login.component.css']
})
export class RegisterComponent {
  @Output() voltar = new EventEmitter<void>();

  nome = new FormControl('', [Validators.required]);
  email = new FormControl('', [Validators.required, Validators.email]);
  senha = new FormControl('', [Validators.required, Validators.minLength(6)]);

  user = {
    nome: '',
    email: '',
    senha: ''
  };

  validaInput(): boolean {
    return this.nome.valid && this.email.valid && this.senha.valid;
  }

  registrar(): void {
    if (this.validaInput()) {
      // Implementar lógica de registro
      console.log('Registrando usuário:', this.user);
    }
  }

  voltarParaLogin(): void {
    this.voltar.emit();
  }
} 