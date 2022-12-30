import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoggingServiceService {

  constructor() { }
  printLog(logMsg: Object) {
    if (environment.debug) {
      console.log(logMsg);
    }
  }  
}
