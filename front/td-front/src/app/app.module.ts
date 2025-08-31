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
import { CancelarOSComponent } from './components/os/cancelar-os/cancelar-os.component';
import { OsCreateSucssesComponent } from './components/os/os-create/os-create-sucsses/os-create-sucsses.component';
import {MatPaginator} from '@angular/material/paginator';
import { OsManagerComponent } from './components/os/os-manager/os-manager.component';
import { MatTabsModule } from '@angular/material/tabs';
import { ItemServiceComponent } from './components/item-service/item-service.component';
import { ItemServiceCreateComponent } from './components/item-service/item-service-create/item-service-create.component';
import { ItemServiceUpdateComponent } from './components/item-service/item-service-update/item-service-update.component';
import { ItemServiceDeleteComponent } from './components/item-service/item-service-delete/item-service-delete.component';
import { ItemServicelMinilistComponent } from './components/item-service/item-servicel-minilist/item-servicel-minilist.component';
import { ItemServiceCreateAvulsoComponent } from './components/item-service/item-service-create-avulso/item-service-create-avulso.component';
import { ItemServiceCobrarhoraComponent } from './components/item-service/item-service-cobrarhora/item-service-cobrarhora.component';
import { RegistroInicialComponent } from './components/registro-inicial/registro-inicial.component';



@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    HomeComponent,
    HeaderComponent,
    ColaboradorListComponent,
    LoginComponent,
    ColaboradorCreateComponent,
    ColaboradorUpdateComponent,
    ColaboradorDeleteComponent,
    ClientesListComponent,
    ClienteCreateComponent,
    ClientesDeleteComponent,
    ClientesUpdateComponent,
    OsCreateComponent,
    OsListComponent,
    ClienteCreateOsComponent,
    CancelarOSComponent,
    OsCreateSucssesComponent,
    OsManagerComponent,
    ItemServiceComponent,
    ItemServiceCreateComponent,
    ItemServiceUpdateComponent,
    ItemServiceDeleteComponent,
    ItemServicelMinilistComponent,
    ItemServiceCreateAvulsoComponent,
    ItemServiceCobrarhoraComponent,
    RegistroInicialComponent
    

    
  
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
    MatTreeModule,
    MatTabsModule,
 
   
    
   
  
  ],
  providers: [AuthInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }


//É para ficar em providers, a classe está comentada durante a verificação do trecho de código. 
//AuthInterceptorProvider
