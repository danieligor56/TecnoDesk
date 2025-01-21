import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-os-manager',
  templateUrl: './os-manager.component.html',
  styleUrls: ['./os-manager.component.css']
})
export class OsManagerComponent implements OnInit {
cli:String = "Daniel igor"
  constructor() { }

  ngOnInit(): void {
  }
  
}
export class AppModule {}