import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { HomeComponent } from './components/home/home.component';
import { ColaboradorListComponent } from './components/colaborador/colaborador-list/colaborador-list.component';

const routes: Routes = [
  {
    path:'',component:NavComponent,children:[
      {path:'home',component:HomeComponent},
      {path:'colaborador',component:ColaboradorListComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }