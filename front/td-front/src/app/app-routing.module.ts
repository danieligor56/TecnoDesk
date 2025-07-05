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
import { ItemServiceComponent } from './components/item-service/item-service.component';
import { ItemServiceCreateComponent } from './components/item-service/item-service-create/item-service-create.component';
import { ItemServiceUpdateComponent } from './components/item-service/item-service-update/item-service-update.component';
import { ItemServiceDeleteComponent } from './components/item-service/item-service-delete/item-service-delete.component';
import { ItemServicelMinilistComponent } from './components/item-service/item-servicel-minilist/item-servicel-minilist.component';
import { ItemServiceCreateAvulsoComponent } from './components/item-service/item-service-create-avulso/item-service-create-avulso.component';
import { ItemServiceCobrarhoraComponent } from './components/item-service/item-service-cobrarhora/item-service-cobrarhora.component';
import { RegistroInicialComponent } from './components/registro-inicial/registro-inicial.component';


const routes: Routes = [
  {
    path:'login',component:LoginComponent
  },{
    path:'registroInicial',component:RegistroInicialComponent
  },
  {
    path:'',component:NavComponent,canActivate: [AuthGuard],children:[
      {path:'home',component:HomeComponent},
      {path: 'itemService',component:ItemServiceComponent},
      {path: 'itemService/create',component:ItemServiceCreateComponent},
      {path: 'itemService/update', component:ItemServiceUpdateComponent},
      {path: 'itemService/delete', component:ItemServiceDeleteComponent},
      {path: 'itemService/minilist',component:ItemServicelMinilistComponent},
      {path: 'itemService/createAvulso',component:ItemServiceCreateAvulsoComponent},
      {path: 'itemService/cobrarHora',component:ItemServiceCobrarhoraComponent},  
      {path:'colaborador',component:ColaboradorListComponent},
      {path:'colaborador/create',component:ColaboradorCreateComponent},
      {path:'colaborador/update/:id',component:ColaboradorUpdateComponent},
      {path:'colaborador/delete/:id',component:ColaboradorDeleteComponent},
      {path:'os/create',component:OsCreateComponent},
      {path:'os/create/:id',component:OsCreateComponent},
      {path:'os/list',component:OsListComponent},
      {path:'os/manager',component:OsManagerComponent},
      {path:'manager/:id',component:OsManagerComponent},
      {path:'clientes',component:ClientesListComponent},

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
