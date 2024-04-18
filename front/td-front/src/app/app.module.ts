import { NgModule } from '@angular/core';
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




@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    HomeComponent,
    HeaderComponent,
    ColaboradorListComponent,
    LoginComponent
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
    HttpClientModule,
    JwtModule
  
  ],
  providers: [AuthInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }


//É para ficar em providers, a classe está comentada durante a verificação do trecho de código. 
//AuthInterceptorProvider
