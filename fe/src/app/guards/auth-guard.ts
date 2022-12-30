import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { LoggingServiceService } from "../shared/services/logging-service.service";

@Injectable({
    providedIn: 'root'
  })
export class AuthGuard implements CanActivate{
    constructor( private log: LoggingServiceService, private route: Router){}
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        throw new Error("Method not implemented.");
    }
    
}