import { Component, OnInit } from '@angular/core';
import { MatTreeFlatDataSource } from '@angular/material/tree';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})

export class NavComponent implements OnInit {
treeos:boolean = false;
  
constructor(private router: Router,private authService:AuthService,private toastr:ToastrService) { }

  ngOnInit(): void {
    this.router.navigate(['home'])
    //home
  }

  logout(){
    this.router.navigate(['login'])
    this.authService.logout();
    this.toastr.info('Logout realizado com sucesso','logout',{timeOut:7000})
  }

  setTreeOs(){
    debugger;
    if(this.treeos == true){
      this.treeos = false;
    }else{
      this.treeos = true;
    }
    
  }



}
