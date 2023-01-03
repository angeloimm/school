import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { ROUTE_PATH, TIPO_UTENTE_VALUES } from "../shared/constants";
import { LoggedUserService } from "../shared/services/logged-user.service";
import { LoggingServiceService } from "../shared/services/logging-service.service";

@Injectable({
    providedIn: 'root'
  })
export class AdminGuard implements CanActivate{
    constructor(    private log: LoggingServiceService, 
                    private loggedUser:LoggedUserService,
                    private router: Router){}
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if( this.loggedUser.getLoggedUSer().tipoUtente === TIPO_UTENTE_VALUES.AMMINISTRATORE ){
            return true;
        }else{
            this.router.navigate([ROUTE_PATH.APP_GENERIC_ERROR_ROUTE],{queryParams:{tipoErrore:'403',init:true}});
            return false;
        }
    }
}

@Injectable({
    providedIn: 'root'
  })
export class DocenteGuard implements CanActivate{
    constructor(    private log: LoggingServiceService, 
                    private loggedUser:LoggedUserService,
                    private router: Router){}
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if( this.loggedUser.getLoggedUSer().tipoUtente === TIPO_UTENTE_VALUES.AMMINISTRATORE || this.loggedUser.getLoggedUSer().tipoUtente === TIPO_UTENTE_VALUES.DOCENTE ){
            return true;
        }else{
            this.router.navigate([ROUTE_PATH.APP_GENERIC_ERROR_ROUTE],{queryParams:{tipoErrore:'403',init:true}});
            return false;
        }
    }
}

@Injectable({
    providedIn: 'root'
  })
export class StudenteGuard implements CanActivate{
    constructor(    private log: LoggingServiceService, 
                    private loggedUser:LoggedUserService,
                    private router: Router){}
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if( this.loggedUser.getLoggedUSer().tipoUtente === TIPO_UTENTE_VALUES.AMMINISTRATORE || this.loggedUser.getLoggedUSer().tipoUtente === TIPO_UTENTE_VALUES.STUDENTE ){
            return true;
        }else{
            this.router.navigate([ROUTE_PATH.APP_GENERIC_ERROR_ROUTE],{queryParams:{tipoErrore:'403',init:true}});
            return false;
        }
    }
}