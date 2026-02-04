import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTreeFlatDataSource } from '@angular/material/tree';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { EmpresaService } from 'src/app/services/empresa.service';
import { MatDrawer } from '@angular/material/sidenav';
import { SidenavService } from 'src/app/services/sidenav.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})

export class NavComponent implements OnInit {
  @ViewChild('drawer') drawer: MatDrawer;
  treeos: boolean = false;
  treeCadastros: boolean = false;
  segmentoEmpresa: number = 0;

  constructor(
    private router: Router,
    private authService: AuthService,
    private toastr: ToastrService,
    private empresaService: EmpresaService,
    private sidenavService: SidenavService) { }

  ngOnInit(): void {
    this.buscarSegmentoEmpresa()
    this.router.navigate(['home'])

    // Subscribe to sidenav service events
    this.sidenavService.sidenavClose$.subscribe(() => {
      this.drawer.close();
    });

    this.sidenavService.sidenavOpen$.subscribe(() => {
      this.drawer.open();
    });

    this.sidenavService.sidenavToggle$.subscribe(() => {
      this.drawer.toggle();
    });
  }

  logout() {
    this.router.navigate(['login'])
    this.authService.logout();
    this.toastr.info('Logout realizado com sucesso', 'logout', { timeOut: 7000 })
  }

  abrirSuporte() {
    const phoneNumber = '5585988584985'; // Número do WhatsApp do suporte
    const message = 'Olá, preciso de suporte técnico.'; // Mensagem padrão
    const whatsappUrl = `https://wa.me/${phoneNumber}?text=${encodeURIComponent(message)}`;

    window.open(whatsappUrl, '_blank');
    this.toastr.info('Abrindo WhatsApp...', 'Suporte', { timeOut: 3000 });
  }

  setTreeOs() {
    this.treeos = !this.treeos;
    if (this.treeos) {
      this.treeCadastros = false;
    }
  }

  setTreeCadastros() {
    this.treeCadastros = !this.treeCadastros;
    if (this.treeCadastros) {
      this.treeos = false;
    }
  }

  buscarSegmentoEmpresa() {
    this.empresaService.buscarSegmentoEmpresa().subscribe(response => {
      this.segmentoEmpresa = response;
    })
  }



}
