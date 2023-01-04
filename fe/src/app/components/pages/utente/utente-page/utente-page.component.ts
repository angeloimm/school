import { Component, OnInit } from '@angular/core';
import { LazyLoadEvent, Message, MessageService } from 'primeng/api';
import { Utente } from 'src/app/models/utente';
import { UtenteApiService } from 'src/app/shared/services/api/utente-api.service';
import {CONST} from 'src/app/shared/constants';
import { ActivatedRoute } from '@angular/router';
import { LoggingServiceService } from 'src/app/shared/services/logging-service.service';
import { TranslateService } from '@ngx-translate/core';
import { RicercaUtenteQueryStringUtils } from './ricerca-utente-query-string-utils';
import { S } from '@fullcalendar/core/internal-common';
import { encodeBase64 } from 'src/app/shared/utils/common-utils';

@Component({
  selector: 'app-utente-page',
  templateUrl: './utente-page.component.html',
  styleUrls: ['./utente-page.component.scss']
})
export class UtentePageComponent implements OnInit {
  utenti: Utente[];
  totalRecords: number;
  loading: boolean;
  selectAll: boolean = false;
  tipoUtente:string;
  utentiSelezionati: Utente[];
  msgs:Message[] = [];

  constructor(private utenteSvc:UtenteApiService,
              private activatedRoute: ActivatedRoute,
              private log:LoggingServiceService,
              private translate:TranslateService,
              private msgSvc:MessageService){

  }
  ngOnInit(): void {
  }

  onSelectionChange(value = []) {
    this.selectAll = value.length === this.totalRecords;
    this.utentiSelezionati = value;
  }
  loadUtenti(event: LazyLoadEvent) {
    this.loading = true;
    //Capisco che tipo di utente caricare...
    this.activatedRoute.queryParams.subscribe(params => {
      let tipoUtente = params['tipoUtente'];
      let parametri = new URLSearchParams();
      const messaggio:Message = {};
      messaggio.severity="info";
      messaggio.summary = this.translate.instant('gestione-utenti.msgs.welcome.summary');
      for(let key in event){
        parametri.set(key, event[key]);
      }
      if( tipoUtente && tipoUtente != '' ){
        parametri.set('tipoUtente', tipoUtente);
        if( tipoUtente === 'A' ){
          messaggio.detail = this.translate.instant('gestione-utenti.msgs.welcome.amministratori');
        }else if( tipoUtente === 'D' ){
          messaggio.detail = this.translate.instant('gestione-utenti.msgs.welcome.docenti');
        }else if( tipoUtente === 'S' ){
          messaggio.detail = this.translate.instant('gestione-utenti.msgs.welcome.studenti');
        }else{
          messaggio.severity = "warn";
          messaggio.detail = this.translate.instant('gestione-utenti.msgs.welcome.unknown',{tipoUtente:tipoUtente});
        }
      }
      this.msgs.push(messaggio);
      const ricercaUtenteQueryStringUtils:RicercaUtenteQueryStringUtils = {};
      ricercaUtenteQueryStringUtils.event = event;
      ricercaUtenteQueryStringUtils.tipoUtente = tipoUtente;
      const queryString:string = JSON.stringify(ricercaUtenteQueryStringUtils);
      const b64Qs:string = encodeBase64(queryString);
      const finalUrl:string = CONST.USERS_PROTECTED_URL+'?q='+b64Qs;      
      this.log.printLog("Ricerco utenti di tipo " + tipoUtente + " su URL: " + finalUrl+" query string: "+queryString);
      this.utenteSvc.get(finalUrl).subscribe({
        next: (response) => {
          this.totalRecords = response.totalRecords;
          this.utenti = response.payload;
          this.loading = false;
        },
        error: (error) =>{
  
          this.loading = false;
        },
      });
    });


    /*setTimeout(() => {
        this.customerService.getCustomers({lazyEvent: JSON.stringify(event)}).then(res => {
            this.customers = res.customers;
            this.totalRecords = res.totalRecords;
            this.loading = false;
        })
    }, 1000);*/
}
  /*
  onSelectAllChange(event) {
      const checked = event.checked;
  
      if (checked) {
          this.customerService.getCustomers().then(res => {
              this.selectedCustomers = res.customers;
              this.selectAll = true;
          });
      }
      else {
          this.utentiSelezionati = [];
          this.selectAll = false;
      }
  }
  */
}

