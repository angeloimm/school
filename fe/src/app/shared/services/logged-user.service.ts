import { Injectable } from '@angular/core';
import { Utente } from 'src/app/models/utente';
import { CONST } from '../constants';

@Injectable({
  providedIn: 'root'
})
export class LoggedUserService {
  
  constructor() { }
  public setLoggedUser(loggedUser:Utente){
    sessionStorage.setItem(CONST.USR_STORAGE_KEY, JSON.stringify(loggedUser));  
  }
  public getLoggedUSer(){
    return JSON.parse(sessionStorage.getItem(CONST.USR_STORAGE_KEY));
  }
  public clearLoggedUSer(){
    sessionStorage.removeItem(CONST.USR_STORAGE_KEY);
  }
}
