import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Message } from 'primeng/api';
import { LayoutService } from 'src/app/components/layout/service/layout.service';
import { FormUtilsService } from 'src/app/shared/services/form-utils.service';
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
  username:FormControl;
  password:FormControl;
  credenzialiUtente:FormGroup;
  constructor( private router:Router,
    private log: LoggingServiceService,
    private translate: TranslateService,
    private fb:FormBuilder,
    public formUtils:FormUtilsService,
    private layoutSvc:LayoutService){

  }
  ngOnInit(): void {
    //Welcom msg
    const welcomMsg:Message = {};
    welcomMsg.severity="info";
    welcomMsg.summary=this.translate.instant('login.msgs.welcome.summary');
    welcomMsg.detail=this.translate.instant('login.msgs.welcome.detail');
    this.msgs.push(welcomMsg);
    //Form
    this.username = new FormControl('', Validators.required);
    this.password = new FormControl('', Validators.required);
    this.credenzialiUtente = this.fb.group({
      username: this.username,
      password: this.password,
    });
  }
  login($event){
    
  }
}
