import { NgModule, Output } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './components/nav/nav.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import { HomeComponent } from './components/home/home.component';
import {MatCardModule} from '@angular/material/card';
import { HeaderComponent } from './components/header/header.component';
import { ColaboradorListComponent } from './components/colaborador/colaborador-list/colaborador-list.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatDialogModule} from '@angular/material/dialog';
import { LoginComponent } from './components/login/login.component';
import {MatInputModule} from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule } from '@angular/common/http';
import { JwtModule } from "@auth0/angular-jwt";
import { AuthInterceptorProvider } from './interceptors/auth.interceptor';
import { ColaboradorCreateComponent } from './components/colaborador/colaborador-create/colaborador-create.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatFormFieldModule} from '@angular/material/form-field';
import { NgxMaskModule } from 'ngx-mask';
import { OSComponent } from './components/os/os.component'
import {MatGridListModule} from '@angular/material/grid-list';
import {MatSelectModule} from '@angular/material/select';
import {MatStepperModule} from '@angular/material/stepper';
import { ColaboradorUpdateComponent } from './components/colaborador/colaborador-update/colaborador-update.component';
import { ColaboradorDeleteComponent } from './components/colaborador/colaborador-delete/colaborador-delete.component';
import { ClientesListComponent } from './components/clientes/cliente-list/clientes-list.component';
import { ClienteCreateComponent } from './components/clientes/cliente-create/cliente-create.component';
import { ClientesDeleteComponent } from './components/clientes/clientes-delete/clientes-delete.component';
import { ClientesUpdateComponent } from './components/clientes/clientes-update/clientes-update.component';
import {MatTreeModule} from '@angular/material/tree';
import { OsCreateComponent } from './components/os/os-create/os-create.component';
import { OsListComponent } from './components/os/os-list/os-list.component';
import { ClienteCreateOsComponent } from './components/os/cliente-create-os/cliente-create-os.component';



@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    HomeComponent,
    HeaderComponent,
    ColaboradorListComponent,
    LoginComponent,
    ColaboradorCreateComponent,
    OSComponent,
    ColaboradorUpdateComponent,
    ColaboradorDeleteComponent,
    ClientesListComponent,
    ClienteCreateComponent,
    ClientesDeleteComponent,
    ClientesUpdateComponent,
    OsCreateComponent,
    OsListComponent,
    ClienteCreateOsComponent
    
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
    MatButtonModule,
    MatCardModule,
    MatPaginatorModule,
    MatTableModule,
    MatDialogModule,
    MatTableModule,
    MatPaginatorModule,
    MatTableModule,
    MatInputModule,
    ReactiveFormsModule,
    FormsModule,
   ToastrModule.forRoot({
      timeOut:3000,
      closeButton:true,
      progressBar:true
    }),
    NgxMaskModule.forRoot({
      
    }),
    HttpClientModule,
    JwtModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatGridListModule,
    MatSelectModule,
    MatStepperModule,
    MatTreeModule
   
    
   
  
  ],
  providers: [AuthInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }


//É para ficar em providers, a classe está comentada durante a verificação do trecho de código. 
//AuthInterceptorProvider
