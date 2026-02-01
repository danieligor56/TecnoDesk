import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() toggleSidenav = new EventEmitter<void>();
  nomeUsuario: string = this.getPrimeirosNomes(sessionStorage.getItem('usuarioNome'))

  onToggleSidenav() {
    this.toggleSidenav.emit();
  }

  constructor(
    private router: Router,
    private authService: AuthService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
  }

  logout() {
    this.router.navigate(['login'])
    this.authService.logout();
    this.toastr.info('Logout realizado com sucesso', 'logout', { timeOut: 7000 })
  }

  getPrimeirosNomes(nome: string): string {
    if (!nome) return '';

    return nome
      .trim()
      .split(' ')
      .filter(p => p.length > 0)
      .slice(0, 2)
      .join(' ');
  }

}
