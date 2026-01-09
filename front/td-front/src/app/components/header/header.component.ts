import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() toggleSidenav = new EventEmitter<void>();
  nomeUsuario: string = this.getPrimeirosNomes(sessionStorage.getItem('usuarioNome'))

  onToggleSidenav(){
    this.toggleSidenav.emit();
  }

  constructor() { }

  ngOnInit(): void {
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
