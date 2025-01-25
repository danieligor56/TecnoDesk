import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { HomeComponent } from './components/home/home.component';
import { ColaboradorListComponent } from './components/colaborador/colaborador-list/colaborador-list.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './auth/auth.guard';
import { ColaboradorCreateComponent } from './components/colaborador/colaborador-create/colaborador-create.component';
import { ColaboradorUpdateComponent } from './components/colaborador/colaborador-update/colaborador-update.component';
import { ColaboradorDeleteComponent } from './components/colaborador/colaborador-delete/colaborador-delete.component';
import { ClientesListComponent } from './components/clientes/cliente-list/clientes-list.component';
import { ClienteCreateComponent } from './components/clientes/cliente-create/cliente-create.component';
import { ClientesUpdateComponent } from './components/clientes/clientes-update/clientes-update.component';
import { ClientesDeleteComponent } from './components/clientes/clientes-delete/clientes-delete.component';
import { OsCreateComponent } from './components/os/os-create/os-create.component';
import { OsListComponent } from './components/os/os-list/os-list.component';
import { OsManagerComponent } from './components/os/os-manager/os-manager.component';


const routes: Routes = [
  {
    path:'login',component:LoginComponent
  },
  {
    path:'',component:NavComponent,canActivate: [AuthGuard],children:[
      {path:'home',component:HomeComponent}, 
      {path:'colaborador',component:ColaboradorListComponent},
      {path:'colaborador/create',component:ColaboradorCreateComponent},
      {path:'colaborador/update/:id',component:ColaboradorUpdateComponent},
      {path:'colaborador/delete/:id',component:ColaboradorDeleteComponent},
      {path:'os/create',component:OsCreateComponent},
      {path:'os/list',component:OsListComponent},
      {path:'os/manager',component:OsManagerComponent},
      {path:'clientes',component:ClientesListComponent},
      {path:'clientes/create',component:ClienteCreateComponent},
      {path:'clientes/update/:id',component:ClientesUpdateComponent},
      {path:'clientes/delete/:id',component:ClientesDeleteComponent}
      
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
