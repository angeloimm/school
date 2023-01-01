import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Message } from 'primeng/api';
import { Utente } from 'src/app/models/utente';
import { LoggingServiceService } from 'src/app/shared/services/logging-service.service';
import { PasswordValidator } from 'src/app/validators/password.validator';

@Component({
  selector: 'app-utente',
  templateUrl: './utente.component.html',
  styleUrls: ['./utente.component.scss']
})
export class UtenteComponent implements OnInit {
  utente: Utente = {};
  msgs: Message[] = [];
  msg: Message = {};
  
  uploadedFiles: any[] = [];
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
  constructor(private route: ActivatedRoute,
    private log: LoggingServiceService,
    private translate: TranslateService,
    private fb:FormBuilder) { }
  ngOnInit(): void {
    this.initPannello();
    this.createForm();
  }
  initPannello(){
    this.utente.attivo = true;
    this.route.queryParams.subscribe(params => {
      let tipoUtente = params['tipoUtente'];
      this.utente.tipoUtente = tipoUtente;
      this.log.printLog("Attivo " + this.utente.attivo + " tipo: " + this.utente.tipoUtente);
      this.msg.summary = this.translate.instant('utente.titolo-pannello');
      this.msg.severity = 'info';
      if (tipoUtente === 'A') {
        this.msg.detail = this.translate.instant('utente.inserimento-amministratore');
      } else if (tipoUtente === 'D') {
        this.msg.detail = this.translate.instant('utente.inserimento-docente');
      } else if (tipoUtente === 'S') {
        this.msg.detail = this.translate.instant('utente.inserimento-studente');
      } else {
        this.msg.severity = 'warning';
        this.msg.detail = this.translate.instant('utente.tipo-utente-sconosciuto', { tipo: tipoUtente });
      }
      this.msgs.push(this.msg);
    });
  }
  createForm(){
    this.nome = new FormControl('',Validators.required);
    this.cognome = new FormControl('',Validators.required);
    this.codiceFiscale = new FormControl('',Validators.required);
    this.dataNascita = new FormControl('',Validators.required);
    this.indirizzo = new FormControl('',Validators.required);
    this.username = new FormControl('',Validators.required);
    //Password e match password
    this.matchingPasswordsGroup = new FormGroup({
      password: new FormControl('', Validators.compose([
        Validators.minLength(5),
        Validators.required,
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$')
      ])),
      confirm_password: new FormControl('', Validators.required)
    }, (formGroup: FormGroup) => {
      return PasswordValidator.areEqual(formGroup);
    });

    this.userDetailsForm = this.fb.group({
      nome: this.nome,
      cognome: this.cognome,
      codiceFiscale: this.codiceFiscale,
      dataNascita: this.dataNascita,
      indirizzo: this.indirizzo,
      username: this.username,
      matchingPasswords: this.matchingPasswordsGroup
    });
  }
  onUpload(event) {
    for (let file of event.files) {
      this.uploadedFiles.push(file);
    }
  }
  onSubmitUserDetails(value: any){
    console.log(value);
  }  
}
