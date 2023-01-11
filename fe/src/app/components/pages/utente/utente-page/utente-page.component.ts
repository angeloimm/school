import { Component, OnInit } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, MessageService } from 'primeng/api';
import { Utente } from 'src/app/models/utente';
import { UtenteApiService } from 'src/app/shared/services/api/utente-api.service';
import { CONST, ROUTE_PATH, TIPO_UTENTE_KEYS } from 'src/app/shared/constants';
import { ActivatedRoute, Router } from '@angular/router';
import { LoggingServiceService } from 'src/app/shared/services/logging-service.service';
import { TranslateService } from '@ngx-translate/core';
import { RicercaUtenteQueryStringUtils } from './ricerca-utente-query-string-utils';
import { encodeBase64 } from 'src/app/shared/utils/common-utils';

@Component({
  selector: 'app-utente-page',
  templateUrl: './utente-page.component.html',
  styleUrls: ['./utente-page.component.scss'],
  providers:[ConfirmationService]
})
export class UtentePageComponent implements OnInit {
  utenti: Utente[];
  totalRecords: number;
  loading: boolean;
  tipoUtente: string;
  lablePulsanteAggiungi:string;
  welcomeMsg:string;
  
  currentLazyLoadEvent:LazyLoadEvent;
  constructor(private utenteSvc: UtenteApiService,
    private activatedRoute: ActivatedRoute,
    private log: LoggingServiceService,
    private translate: TranslateService,
    private confirmationService:ConfirmationService, 
    private messageService:MessageService,
    private router:Router) {

  }
  ngOnInit(): void {
  }

  modificaUtente(utente:Utente){
    this.router.navigate([ROUTE_PATH.APP_ADD_UTENTE_ROUTE],{queryParams:{id:utente.id}});
  }
  cancellaUtente(utente:Utente){
    this.confirmationService.confirm({
      message: this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.messaggio-conferma',{nome: utente.nome, cognome: utente.cognome}),
      accept:() => {
        this.deleteUtente(utente);
      },
      reject:() => {
        
      }
    });
  }  
  deleteUtente(utente:Utente){
    const finalUrl: string = CONST.USERS_PROTECTED_URL + '/'+utente.id;
    this.utenteSvc.deleteT(finalUrl).subscribe({
      next: (response) => {
        //Mostro esito
        this.messageService.add({severity:'success', summary:this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.summary-esito'), detail:this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.detail-esito-ok')});
        //ricarico i dati
        this.loadUtenti(this.currentLazyLoadEvent);
      },
      error: (e) =>{
        this.messageService.add({severity:'error', summary:this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.summary-esito'), detail:this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.detail-esito-ko')});
      }
    });
  }
  loadUtenti(event: LazyLoadEvent) {
    this.currentLazyLoadEvent = event;
    this.loading = true;
    //Capisco che tipo di utente caricare...
    this.activatedRoute.queryParams.subscribe(params => {
      this.tipoUtente = params['tipoUtente'];
      let parametri = new URLSearchParams();
      for (let key in event) {
        parametri.set(key, event[key]);
      }
      if (this.tipoUtente && this.tipoUtente != '') {
        parametri.set('tipoUtente', this.tipoUtente);
        if (this.tipoUtente === TIPO_UTENTE_KEYS.AMMINISTRATORE) {
          this.welcomeMsg = this.translate.instant('gestione-utenti.msgs.welcome.amministratori');
          this.lablePulsanteAggiungi = this.translate.instant('gestione-utenti.msgs.azioni.aggiungi-amministratore');
        } else if (this.tipoUtente === TIPO_UTENTE_KEYS.DOCENTE) {
          this.welcomeMsg = this.translate.instant('gestione-utenti.msgs.welcome.docenti');
          this.lablePulsanteAggiungi = this.translate.instant('gestione-utenti.msgs.azioni.aggiungi-docente');
        } else if (this.tipoUtente === TIPO_UTENTE_KEYS.STUDENTE) {
          this.welcomeMsg = this.translate.instant('gestione-utenti.msgs.welcome.studenti');
          this.lablePulsanteAggiungi = this.translate.instant('gestione-utenti.msgs.azioni.aggiungi-studente');
        } else {
          this.welcomeMsg = this.translate.instant('gestione-utenti.msgs.welcome.unknown', { tipoUtente: this.tipoUtente });
        }
      }
      const ricercaUtenteQueryStringUtils: RicercaUtenteQueryStringUtils = {};
      ricercaUtenteQueryStringUtils.event = event;
      ricercaUtenteQueryStringUtils.tipoUtente = this.tipoUtente;
      const queryString: string = JSON.stringify(ricercaUtenteQueryStringUtils);
      const b64Qs: string = encodeBase64(queryString);
      const finalUrl: string = CONST.USERS_PROTECTED_URL + '?q=' + b64Qs;
      this.log.printLog("Ricerco utenti di tipo " + this.tipoUtente + " su URL: " + finalUrl + " query string: " + queryString);
      this.utenteSvc.get(finalUrl).subscribe({
        next: (response) => {
          this.totalRecords = response.totalRecords;
          this.utenti = response.payload;
          this.loading = false;
        },
        error: (error) => {

          this.loading = false;
        },
      });
    });
  }
  gotoPage($event){
    this.router.navigate([ROUTE_PATH.APP_ADD_UTENTE_ROUTE],{queryParams:{tipoUtente:this.tipoUtente}});  
  }
}

