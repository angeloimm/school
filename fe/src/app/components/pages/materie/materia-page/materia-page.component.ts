import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, LazyLoadEvent, MessageService } from 'primeng/api';
import { Materia } from 'src/app/models/materia';
import { Utente, UtenteAutocomplete } from 'src/app/models/utente';
import { CONST, TIPO_UTENTE_KEYS } from 'src/app/shared/constants';
import { MateriaApiService } from 'src/app/shared/services/api/materia-api.service';
import { UtenteApiService } from 'src/app/shared/services/api/utente-api.service';
import { FormUtilsService } from 'src/app/shared/services/form-utils.service';
import { LoggingServiceService } from 'src/app/shared/services/logging-service.service';
import { encodeBase64 } from 'src/app/shared/utils/common-utils';
import { RicercaUtenteQueryStringUtils } from '../../utente/utente-page/ricerca-utente-query-string-utils';

@Component({
  selector: 'app-materia-page',
  templateUrl: './materia-page.component.html',
  styleUrls: ['./materia-page.component.scss'],
  providers: [ConfirmationService]
})
export class MateriaPageComponent implements OnInit {
  totalRecords: number;
  loading: boolean;
  lablePulsanteAggiungi: string;
  currentLazyLoadEvent: LazyLoadEvent;
  welcomeMsg: string;
  display: boolean = false;
  materie: Materia[] = [];
  materiaForm: FormGroup;
  results: UtenteAutocomplete[] = [];
  nomeMateria: FormControl;
  docentiMateria: FormControl;
  constructor(
    private log: LoggingServiceService,
    private translate: TranslateService,
    private fb: FormBuilder,
    public formUtils: FormUtilsService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private utenteSvc:UtenteApiService,
    private materiaSvc: MateriaApiService) { }
  ngOnInit(): void {
    this.createFormGroup(null);
  }
  createFormGroup(materiaSelezionata: Materia) {
    this.nomeMateria = new FormControl(materiaSelezionata ? materiaSelezionata != null ? materiaSelezionata.nomeMateria : '' : '', { validators: [Validators.required] });
    this.docentiMateria = new FormControl('', { validators: [Validators.required] });
    this.materiaForm = this.fb.group({
      nomeMateria: this.nomeMateria,
      docentiMateria: this.docentiMateria
    })
  }
  searchDocenti(event) {
    debugger;
    alert(event);
    const nomeMadocente: string = event.query;
    const ricercaUtenteQueryStringUtils: RicercaUtenteQueryStringUtils = {};
    ricercaUtenteQueryStringUtils.tipoUtente = TIPO_UTENTE_KEYS.DOCENTE;
    const queryString: string = JSON.stringify(ricercaUtenteQueryStringUtils);
    const b64Qs: string = encodeBase64(queryString);
    let parametri = new URLSearchParams();
    parametri.set('q',b64Qs);
    parametri.set('nome-cognome',nomeMadocente);
    const finalUrl: string = CONST.USERS_PROTECTED_URL + '?'+parametri.toString();
    this.log.printLog("Ricerco materie su URL: " + finalUrl + " query string: " + queryString);
    this.utenteSvc.get(finalUrl).subscribe({
      next: (response) => {
        const utenti:Utente[] = response.payload;
        this.results = [];
        utenti.forEach((utente)=>{
          this.results.push({name:utente.nome+" "+utente.cognome, code:utente.id});
        });
      },
      error: (error) => {

        this.loading = false;
      },
    });
  }
  cancellaMateria(materia: Materia) {
    this.confirmationService.confirm({
      message: this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.messaggio-conferma', { nome: materia.nomeMateria }),
      accept: () => {
        this.deleteMateria(materia);
      },
      reject: () => {

      }
    });
  }
  aggiungiModificaMateria(materia: Materia) {
    if (materia && materia != null) {

    } else {
      this.display = true;
    }
  }
  deleteMateria(utente: Materia) {
    const finalUrl: string = CONST.MATERIA_PROTECTED_URL + '/' + utente.id;
    this.materiaSvc.deleteT(finalUrl).subscribe({
      next: (response) => {
        //Mostro esito
        this.messageService.add({ severity: 'success', summary: this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.summary-esito'), detail: this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.detail-esito-ok') });
        //ricarico i dati
        this.loadMaterie(this.currentLazyLoadEvent);
      },
      error: (e) => {
        this.messageService.add({ severity: 'error', summary: this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.summary-esito'), detail: this.translate.instant('gestione-utenti.tabella-utenti.azioni.cancellazione-utente.detail-esito-ko') });
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
