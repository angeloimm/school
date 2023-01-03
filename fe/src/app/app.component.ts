
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CONST, ROUTE_PATH, TIPO_UTENTE_VALUES } from './shared/constants';
import { InitDbServiceService } from './shared/services/api/init-db-service.service';
import { LoggingServiceService } from './shared/services/logging-service.service';
import { TranslateService } from '@ngx-translate/core';
import { PrimeNGConfig } from 'primeng/api';
import { LayoutService } from './components/layout/service/layout.service';
import { LoggedUserService } from './shared/services/logged-user.service';
import { Utente } from './models/utente';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  loading:boolean = false;
  constructor(private log:LoggingServiceService, 
              private initDb:InitDbServiceService,
              private router:Router,
              private translate:TranslateService,
              private loggedUserSvc:LoggedUserService,
              private config: PrimeNGConfig,
              public layoutService:LayoutService){

              }
  ngOnInit(): void {
    this.log.printLog("Controllo se necessario inizializzare o meno");
    this.initApplication();
  }
  private initApplication(){
    this.translate.setDefaultLang('it');
    this.translate.use('it');
    this.translate.get('primeng').subscribe(res => this.config.setTranslation(res));
    this.initDb.get(CONST.INIT_DB_URL).subscribe((response)=>{
      if(response.payload===true){
        this.router.navigate([ROUTE_PATH.APP_INIT_ROUTE],{queryParams:{tipoUtente:'A',init:true}});
      }else{
        if( this.loggedUserSvc.getLoggedUSer() != null ){
          const loggedUser:Utente = this.loggedUserSvc.getLoggedUSer();
          if( loggedUser.tipoUtente === TIPO_UTENTE_VALUES.AMMINISTRATORE ){
            this.router.navigate([ROUTE_PATH.APP_HP_AMMINISTRATORE_ROUTE]);  
          }else if( loggedUser.tipoUtente === TIPO_UTENTE_VALUES.DOCENTE ){
            this.router.navigate([ROUTE_PATH.APP_HP_DOCENTE_ROUTE]);  
          }else if( loggedUser.tipoUtente === TIPO_UTENTE_VALUES.STUDENTE ){
            this.router.navigate([ROUTE_PATH.APP_HP_STUDENTE_ROUTE]);  
          }
        }else{
        
          this.router.navigate([ROUTE_PATH.APP_LOGIN_ROUTE]);
        }
      }
    });
  }
}
