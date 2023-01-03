import { Injectable } from '@angular/core';
import { Utente } from 'src/app/models/utente';
import { CONST, TIPO_UTENTE_VALUES } from '../constants';

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
  public isAmministratore(){

    const luString:string = sessionStorage.getItem(CONST.USR_STORAGE_KEY);
    if(luString == null || luString ===''){
      return false;
    }
    const lu:Utente = JSON.parse(luString);
    if( lu && lu.tipoUtente ===TIPO_UTENTE_VALUES.AMMINISTRATORE ){
      return true;
    }else{
      return false;
    }
  }
  public isDocente(){

    const luString:string = sessionStorage.getItem(CONST.USR_STORAGE_KEY);
    if(luString == null || luString ===''){
      return false;
    }
    const lu:Utente = JSON.parse(luString);
    if( lu && lu.tipoUtente ===TIPO_UTENTE_VALUES.DOCENTE ){
      return true;
    }else{
      return false;
    }
  }  
  public isStudente(){

    const luString:string = sessionStorage.getItem(CONST.USR_STORAGE_KEY);
    if(luString == null || luString ===''){
      return false;
    }
    const lu:Utente = JSON.parse(luString);
    if( lu && lu.tipoUtente ===TIPO_UTENTE_VALUES.STUDENTE ){
      return true;
    }else{
      return false;
    }
  }
}
