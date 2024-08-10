import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { HomeComponent } from './components/home/home.component';
import { ColaboradorListComponent } from './components/colaborador/colaborador-list/colaborador-list.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './auth/auth.guard';
import { ColaboradorCreateComponent } from './components/colaborador/colaborador-create/colaborador-create.component';
import { OSComponent } from './components/os/os.component';
import { ColaboradorUpdateComponent } from './components/colaborador/colaborador-update/colaborador-update.component';
import { ColaboradorDeleteComponent } from './components/colaborador/colaborador-delete/colaborador-delete.component';
import { ClientesListComponent } from './clientes/clientes-list/clientes-list.component';

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
      {path:'os',component:OSComponent},
      {path:'clientes',component:ClientesListComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
