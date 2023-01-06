import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators, ValidationErrors } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Message, MessageService } from 'primeng/api';
import { FileUpload } from 'primeng/fileupload';
import { LayoutService } from 'src/app/components/layout/service/layout.service';
import { Sesso, Utente } from 'src/app/models/utente';
import { CONST, ROUTE_PATH, TIPO_UTENTE_KEYS, TIPO_UTENTE_VALUES } from 'src/app/shared/constants';
import { InitDbServiceService } from 'src/app/shared/services/api/init-db-service.service';
import { FormUtilsService } from 'src/app/shared/services/form-utils.service';
import { LoggingServiceService } from 'src/app/shared/services/logging-service.service';
import { PasswordValidator } from 'src/app/validators/password.validator';
import { UniqueUsernameValidator } from 'src/app/validators/unique-username-validator';

@Component({
  selector: 'app-utente',
  templateUrl: './utente.component.html',
  styleUrls: ['./utente.component.scss']
})
export class UtenteComponent implements OnInit {
  @ViewChild('uploaderAllegatiUtente') 
  uploaderAllegatiUtente:FileUpload;
  utente: Utente = {};
  welcomeMsg:string;
  maxDateValue:Date = new Date();
  multipleUpload: boolean = true;
  //Form utils
  userDetailsForm: FormGroup;
  matchingPasswordsGroup: FormGroup;
  nome: FormControl;
  cognome: FormControl;
  codiceFiscale: FormControl;
  indirizzo: FormControl;
  dataNascita: FormControl;
  username: FormControl;
  password: FormControl;
  confermaPassword: FormControl;
  sessoFc: FormControl;
  elencoSessi:Sesso[] = [];
  selectedSesso:Sesso;
  method:string = "POST";
  inizializzazioneDb:boolean;

  allegatiCaricati:File[];
  private initDbSaveAdminUrl:string = CONST.INIT_DB_SAVE_ADMIN_URL;
  private protectedSaveUserUrl:string = CONST.USERS_PROTECTED_URL;
  uploadUrl:string;
  constructor(private activatedRoute: ActivatedRoute,
    private router:Router,
    private log: LoggingServiceService,
    private translate: TranslateService,
    private fb:FormBuilder,
    public formUtils:FormUtilsService,
    private layoutSvc:LayoutService,
    private initDb:InitDbServiceService,
    private messageService:MessageService) { }
  ngOnInit(): void {
    this.initPannello();
    this.initSesso();
    this.createForm();
  }
  initPannello(){
    
    this.utente.attivo = true;
    this.activatedRoute.queryParams.subscribe(params => {
      let tipoUtente = params['tipoUtente'];

      if (tipoUtente === TIPO_UTENTE_KEYS.AMMINISTRATORE) {
        this.utente.tipoUtente = TIPO_UTENTE_VALUES.AMMINISTRATORE;
        this.welcomeMsg = this.translate.instant('utente.inserimento-amministratore');
      } else if (tipoUtente === TIPO_UTENTE_KEYS.DOCENTE) {
        this.utente.tipoUtente = TIPO_UTENTE_VALUES.DOCENTE;
        this.welcomeMsg = this.translate.instant('utente.inserimento-docente');
      } else if (tipoUtente === TIPO_UTENTE_KEYS.STUDENTE) {
        this.utente.tipoUtente = TIPO_UTENTE_VALUES.STUDENTE;
        this.welcomeMsg = this.translate.instant('utente.inserimento-studente');
      } else {
        this.welcomeMsg = this.translate.instant('utente.tipo-utente-sconosciuto', { tipo: tipoUtente });
      }
      let init:string = params['init'];
      if( init != null && init==="true" ){
        this.inizializzazioneDb = true;
        this.uploadUrl = this.initDbSaveAdminUrl;
        this.layoutSvc.hideTopBarInfoPanel();
      }else{
        this.inizializzazioneDb = false;
        this.uploadUrl = this.protectedSaveUserUrl;
        this.layoutSvc.showTopBarInfoPanel();
      }
      this.log.printLog("Attivo " + this.utente.attivo + " tipo: " + this.utente.tipoUtente);
    });
  }
  initSesso(){
    
    let uomo:Sesso = {code:'M', name:'Uomo'};
    let donna:Sesso = {code:'F', name:'Donna'};
    let altro:Sesso = {code:'O', name:'Altro'};
    this.elencoSessi = [uomo, donna, altro];
  }
  private getSessoUtente(){
    if( this.utente.sesso == null ){
      return null;
    }
    if(this.utente.sesso === 'M'){
      return this.elencoSessi[0];
    }else if(this.utente.sesso === 'F'){
      return this.elencoSessi[1];
    }else{
      return this.elencoSessi[2];
    }
  }
  createForm(){
    this.nome = new FormControl(this.utente.nome,{validators: Validators.required});
    this.cognome = new FormControl(this.utente.cognome,{validators: Validators.required});
    this.codiceFiscale = new FormControl(this.utente.codiceFiscale,{validators:[Validators.required, Validators.pattern("^[A-Za-z]{6}[0-9]{2}[A-Za-z]{1}[0-9]{2}[A-Za-z]{1}[0-9]{3}[A-Za-z]{1}$")]});
    this.dataNascita = new FormControl(this.utente.dataNascita,{validators: Validators.required});
    this.sessoFc = new FormControl(this.getSessoUtente, {validators: Validators.required});
    this.indirizzo = new FormControl(this.utente.indirizzo, {validators: Validators.required});
    this.username = new FormControl(this.utente.username,{validators: [ Validators.required, Validators.minLength(3)], asyncValidators: [UniqueUsernameValidator.validateUsername(this.initDb)]});
    this.password = new FormControl(null,Validators.compose([
      Validators.minLength(8),
      Validators.required,
      Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$')
    ]));
    this.confermaPassword = new FormControl('',{validators: Validators.required});
    //Password e match password
    this.matchingPasswordsGroup = new FormGroup({
      password: this.password,
      confermaPassword: this.confermaPassword
    }, (formGroup: FormGroup)=>{
          return PasswordValidator.areEqual(formGroup);
    });

    this.userDetailsForm = this.fb.group({
      nome: this.nome,
      cognome: this.cognome,
      codiceFiscale: this.codiceFiscale,
      dataNascita: this.dataNascita,
      sesso:this.elencoSessi,
      indirizzo: this.indirizzo,
      username: this.username,
      matchingPasswords: this.matchingPasswordsGroup
    });
  }

  sendData(event){
    
    this.uploaderAllegatiUtente.upload();
    console.log(event);
  }  
  formInvalid(){
    let errori: ValidationErrors = this.userDetailsForm.get("matchingPasswords").errors;
    
    alert(errori);
  }
  rimuoviFile(fileName){
    
    this.uploaderAllegatiUtente.files.forEach((file, indice)=>{

      if( file.name === fileName ) this.uploaderAllegatiUtente.files.splice(indice,1);
    });
  }
  toHumanFileSize( bytes, si:boolean=false, dp:number=1 ){
    const thresh = si ? 1000 : 1024;

    if (Math.abs(bytes) < thresh) {
      return bytes + ' B';
    }
    const units = si 
      ? ['kB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'] 
      : ['KiB', 'MiB', 'GiB', 'TiB', 'PiB', 'EiB', 'ZiB', 'YiB'];
    let u = -1;
    const r = 10**dp;

    do {
      bytes /= thresh;
      ++u;
    } while (Math.round(Math.abs(bytes) * r) / r >= thresh && u < units.length - 1);
    return bytes.toFixed(dp) + ' ' + units[u];
  }
  addData(event){
    
    this.utente.nome = this.nome.value;
    this.utente.cognome = this.cognome.value;
    this.utente.codiceFiscale = this.codiceFiscale.value;
    this.utente.dataNascita = this.dataNascita.value;
    this.selectedSesso = this.userDetailsForm.get('sesso').value;
    if( this.selectedSesso instanceof Object ){
    
      this.utente.sesso = this.selectedSesso.code;
    }else{
      this.utente.sesso = this.selectedSesso;
    }
    this.utente.indirizzo = this.indirizzo.value;
    this.utente.username = this.username.value;
    this.utente.password = this.password.value;
    event.formData.append( "datiUtente", JSON.stringify(this.utente) );
  }
  uploadFinished(event){
    //Messaggio di OK upload/salvataggio
    let messaggio:Message = {};
    messaggio.severity = 'success';
    messaggio.summary = this.translate.instant('uploader.upload-ok-summary');
    messaggio.detail = this.translate.instant('uploader.upload-ok-detail');
    this.messageService.add(messaggio);
    //Svuoto i file
    this.allegatiCaricati = this.uploaderAllegatiUtente.files;
    this.uploaderAllegatiUtente.files = [];
    //se inizializzazione db --> redirect alla login se tutto OK
      setTimeout(()=>{
        if(this.inizializzazioneDb){
          messaggio.severity = 'info';
          messaggio.summary = this.translate.instant('initdb.msgs.summary');
          messaggio.detail = this.translate.instant('initdb.msgs.detail');
          this.messageService.add(messaggio);
          this.router.navigate([ROUTE_PATH.APP_LOGIN_ROUTE]);
        }else{
          if( this.utente.tipoUtente === TIPO_UTENTE_VALUES.DOCENTE ){
            this.router.navigate([ROUTE_PATH.APP_HP_UTENTE_ROUTE],{queryParams:{tipoUtente:TIPO_UTENTE_KEYS.DOCENTE}});
          }else if( this.utente.tipoUtente === TIPO_UTENTE_VALUES.STUDENTE ){
            this.router.navigate([ROUTE_PATH.APP_HP_UTENTE_ROUTE],{queryParams:{tipoUtente:TIPO_UTENTE_KEYS.STUDENTE}});

          }else if( this.utente.tipoUtente === TIPO_UTENTE_VALUES.AMMINISTRATORE ){
            this.router.navigate([ROUTE_PATH.APP_HP_UTENTE_ROUTE],{queryParams:{tipoUtente:TIPO_UTENTE_KEYS.AMMINISTRATORE}});
          }
        }
      }, 5000);
  }
  uploadError($event){
    let messaggio:Message = {};
    messaggio.severity = 'error';
    messaggio.summary = this.translate.instant('uploader.upload-ko-summary');
    messaggio.detail = this.translate.instant('uploader.upload-ko-detail');
    
    this.messageService.add(messaggio);
  }
}
