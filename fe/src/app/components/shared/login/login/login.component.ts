import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Message, MessageService } from 'primeng/api';
import { LayoutService } from 'src/app/components/layout/service/layout.service';
import { ApiResponse } from 'src/app/models/api-response-models';
import { Utente } from 'src/app/models/utente';
import { CONST, ROUTE_PATH, TIPO_UTENTE_VALUES as TIPO_UTENTE_VALUES } from 'src/app/shared/constants';
import { FormUtilsService } from 'src/app/shared/services/form-utils.service';
import { LoggedUserService } from 'src/app/shared/services/logged-user.service';
import { LoggingServiceService } from 'src/app/shared/services/logging-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
/**"login":{
        "msgs":{
            "welcome":{
                "summary":"Accesso al sistema",
                "detail":"Inserisci username e password per accedere al sistema"
            }
        },
        "btn":{
            "accedi":"Login"
        }
    }, */
export class LoginComponent implements OnInit {
  msgs: Message[] = [];
  username: FormControl;
  password: FormControl;
  credenzialiUtente: FormGroup;
  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private log: LoggingServiceService,
    private translate: TranslateService,
    private fb: FormBuilder,
    public formUtils: FormUtilsService,
    private layoutSvc: LayoutService,
    private loggedUserSvc: LoggedUserService,
    private msgService: MessageService,
    private http: HttpClient) {

  }
  ngOnInit(): void {
    //Welcom msg
    const welcomMsg: Message = {};
    welcomMsg.severity = "info";
    welcomMsg.summary = this.translate.instant('login.msgs.welcome.summary');
    welcomMsg.detail = this.translate.instant('login.msgs.welcome.detail');
    this.msgs.push(welcomMsg);
    //Errore di login?
    this.activatedRoute.queryParams.subscribe(params => {
      //Caso di errore?
      let errorCode = params['errorCode'];
      console.log("errorCode "+errorCode);
      if (errorCode && errorCode === '496') {
        const badCredentials: Message = {};
        badCredentials.severity = "error";
        badCredentials.summary = this.translate.instant('login.msgs.badcredentials.summary');
        badCredentials.detail = this.translate.instant('login.msgs.badcredentials.detail');
        this.msgService.add(badCredentials);
      
      }else if (errorCode && errorCode === '403') {
        const badCredentials: Message = {};
        welcomMsg.summary = this.translate.instant('login.msgs.'+errorCode+'.summary');
        welcomMsg.detail = this.translate.instant('login.msgs.'+errorCode+'.detail');
        welcomMsg.severity = 'warn';
        this.msgService.add(badCredentials);
        
        console.log(badCredentials);
      }
      //Caso di logout?
      let logout = params['logout'];
      if (logout && logout === 'true') {
        const logout: Message = {};
        logout.severity = "success";
        logout.summary = this.translate.instant('login.msgs.logout.summary');
        logout.detail = this.translate.instant('login.msgs.logout.detail');
        this.msgService.add(logout);
        console.log(logout);
      }
    });
    //Form
    this.username = new FormControl('', Validators.required);
    this.password = new FormControl('', Validators.required);
    this.credenzialiUtente = this.fb.group({
      username: this.username,
      password: this.password,
    });
    this.layoutSvc.hideTopBarInfoPanel();
  }
  login($event) {
    let body = new URLSearchParams();
    body.set('username', this.username.value);
    body.set('password', this.password.value);
    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };

    this.http
      .post<ApiResponse<Utente>>(CONST.LOGIN_APP_URL, body.toString(), options)
      .subscribe({
        next: response => {
          //Conservo nel service le info dell'utente
          const utenteLoggato: Utente = response.payload;
          this.loggedUserSvc.setLoggedUser(utenteLoggato);
          //Controllo se amministraotre o altro
          const tipoUtente: string = utenteLoggato.tipoUtente;
          if (tipoUtente === TIPO_UTENTE_VALUES.AMMINISTRATORE) {
            //redirigo alla HP ammministratore
            this.router.navigate([ROUTE_PATH.APP_HP_AMMINISTRATORE_ROUTE]);
          } else if (tipoUtente === TIPO_UTENTE_VALUES.DOCENTE) {
            //redirigo alla HP docente
          } else if (tipoUtente === TIPO_UTENTE_VALUES.STUDENTE) {
            //redirigo alla HP studente
          }
        },
        error: (error: HttpErrorResponse) => {
          //Redirigo alla pagina di cortesia
          this.router.navigate([ROUTE_PATH.APP_LOGIN_ROUTE], { queryParams: { errorCode: error.status } });
        }
      });
  }
}
