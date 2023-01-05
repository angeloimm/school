import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { identifierName } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CONST, ROUTE_PATH, TIPO_UTENTE_KEYS } from 'src/app/shared/constants';
import { LoggedUserService } from 'src/app/shared/services/logged-user.service';
import { LayoutService } from '../service/layout.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit {
  public displayErrorDialog:boolean = false;
  constructor(public layoutService: LayoutService,
    private router: Router,
    private http: HttpClient,
    public loggedUserService: LoggedUserService) { }
  ngOnInit(): void {
  }
  public goToPage($event, page: string) {
    if (page === 'schedule') {
      this.router.navigate([ROUTE_PATH.APP_HP_AMMINISTRATORE_ROUTE]);
    } else if (page === 'docenti') {
      
      this.router.navigate([ROUTE_PATH.APP_HP_UTENTE_ROUTE],{queryParams:{tipoUtente:TIPO_UTENTE_KEYS.DOCENTE}});
    } else if (page === 'studenti') {
      this.router.navigate([ROUTE_PATH.APP_HP_UTENTE_ROUTE],{queryParams:{tipoUtente:TIPO_UTENTE_KEYS.STUDENTE}});
    } else {

      this.router.navigate([ROUTE_PATH.APP_GENERIC_ERROR_ROUTE],{queryParams:{tipoErrore:404}});
    }
  }
  logout(event) {
    
    let options = {
      headers: new HttpHeaders()
                  .set('Content-Type', 'application/x-www-form-urlencoded')
    
    };

    this.http
      .post(CONST.LOGOUT_APP_URL, null, options)
      .subscribe({
        next: response => {
          //logout ok, rimuovo dallo storage
          this.loggedUserService.clearLoggedUSer();
          //redirect alla pagina di login
          this.router.navigate([ROUTE_PATH.APP_LOGIN_ROUTE],{queryParams:{logout:true}});
        },
        error: (error: HttpErrorResponse) => {
          //Mostro dialog
          this.displayErrorDialog = true;
        }
      });
  }
}
