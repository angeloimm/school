import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { ApiResponse } from './models/api-response-models';
import { CONST } from './shared/constants';
import { InitDbServiceService } from './shared/services/api/init-db-service.service';
import { LoggingServiceService } from './shared/services/logging-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  loading:boolean = false;
  constructor(private log:LoggingServiceService, 
              private initDb:InitDbServiceService,
              private router:Router){}
  ngOnInit(): void {
    this.log.printLog("Controllo se necessario inizializzare o meno");
    this.initApplication();
  }
  private initApplication(){
    this.loading = true;
    this.initDb.get(CONST.INIT_DB_URL).subscribe((response)=>{
      if(response.payload===true){
        this.router.navigate(["init/applicazione"],{queryParams:{tipoUtente:'A'}});
      }
    });
  }
}
