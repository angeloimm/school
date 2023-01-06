import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, LazyLoadEvent, MessageService } from 'primeng/api';
import { Materia } from 'src/app/models/materia';
import { CONST } from 'src/app/shared/constants';
import { MateriaApiService } from 'src/app/shared/services/api/materia-api.service';
import { LoggingServiceService } from 'src/app/shared/services/logging-service.service';
import { encodeBase64 } from 'src/app/shared/utils/common-utils';
import { RicercaUtenteQueryStringUtils } from '../../utente/utente-page/ricerca-utente-query-string-utils';

@Component({
  selector: 'app-materia-page',
  templateUrl: './materia-page.component.html',
  styleUrls: ['./materia-page.component.scss'],
  providers:[ConfirmationService]
})
export class MateriaPageComponent implements OnInit{
  totalRecords: number;
  loading: boolean;
  lablePulsanteAggiungi:string;
  currentLazyLoadEvent:LazyLoadEvent;
  welcomeMsg:string;
  materie:Materia[] = [];
  constructor(
    private log: LoggingServiceService,
    private translate: TranslateService,
    private confirmationService:ConfirmationService, 
    private messageService:MessageService,
    private materiaSvc:MateriaApiService){}
  ngOnInit(): void {
  }
  cancellaMateria(materia:Materia){
    this.confirmationService.confirm({
      message: this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.messaggio-conferma',{nome: materia.nomeMateria}),
      accept:() => {
        this.deleteMateria(materia);
      },
      reject:() => {
        
      }
    });
  }  
  aggiungiModificaMateria(materia:Materia){

  }
  deleteMateria(utente:Materia){
    const finalUrl: string = CONST.MATERIA_PROTECTED_URL + '/'+utente.id;
    this.materiaSvc.deleteT(finalUrl).subscribe({
      next: (response) => {
        //Mostro esito
        this.messageService.add({severity:'success', summary:this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.summary-esito'), detail:this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.detail-esito-ok')});
        //ricarico i dati
        this.loadMaterie(this.currentLazyLoadEvent);
      },
      error: (e) =>{
        this.messageService.add({severity:'error', summary:this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.summary-esito'), detail:this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.detail-esito-ko')});
      }
    });
  }
  loadMaterie(event: LazyLoadEvent) {
    this.currentLazyLoadEvent = event;
    this.loading = true;
    const ricercaUtenteQueryStringUtils: RicercaUtenteQueryStringUtils = {};
      ricercaUtenteQueryStringUtils.event = event;
      
      const queryString: string = JSON.stringify(ricercaUtenteQueryStringUtils);
      const b64Qs: string = encodeBase64(queryString);
      const finalUrl: string = CONST.MATERIA_PROTECTED_URL + '?q=' + b64Qs;
      this.log.printLog("Ricerco materie su URL: " + finalUrl + " query string: " + queryString);
      this.materiaSvc.get(finalUrl).subscribe({
        next: (response) => {
          this.totalRecords = response.totalRecords;
          this.materie = response.payload;
          this.loading = false;
        },
        error: (error) => {

          this.loading = false;
        },
      });
  }
}
