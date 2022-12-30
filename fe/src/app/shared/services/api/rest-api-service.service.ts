import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Message, MessageService } from 'primeng/api';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RestApiServiceService<T, S> {
  errorMessage:Message = {};  
  constructor(private http:HttpClient, private messageService:MessageService) { }
  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };
  // HttpClient API get() method => Fetch Ts list
  get(apiURL:string): Observable<T> {
    return this.http
      .get<T>(apiURL, this.httpOptions)
      .pipe(retry(1), catchError(this.handleError));
  }
   // HttpClient API post() method => Create T
  createT(apiURL:string, S: any): Observable<T> {
    return this.http
      .post<T>(
        apiURL,
        JSON.stringify(S),
        this.httpOptions
      )
      .pipe(retry(1), catchError(this.handleError));
  }
  // HttpClient API put() method => Update T
  updateT(apiURL: string, S: any): Observable<T> {
    return this.http
      .put<T>(
        apiURL,
        JSON.stringify(S),
        this.httpOptions
      )
      .pipe(retry(1), catchError(this.handleError));
  }
  // HttpClient API delete() method => Delete T
  deleteT(apiURL:string) {
    return this.http
      .delete<T>(apiURL, this.httpOptions)
      .pipe(retry(1), catchError(this.handleError));
  }
  // Error handling
  handleError(error: any) {
    this.errorMessage.severity = "error";
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      
      this.errorMessage.summary = "Errore client";
      this.errorMessage.detail = error.error.message;
    } else {
      // Get server-side error
      this.errorMessage.summary = "Errore server "+error.status;
      this.errorMessage.detail = error.message;
    }
    
    this.messageService.add(this.errorMessage);
    return throwError(() => {
      return this.errorMessage.detail;
    });
  }
}