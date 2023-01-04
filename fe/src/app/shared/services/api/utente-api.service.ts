import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { ApiResponse, PagedApiResponse } from 'src/app/models/api-response-models';
import { Utente } from 'src/app/models/utente';
import { RestApiServiceService } from './rest-api-service.service';

@Injectable({
  providedIn: 'root'
})
export class UtenteApiService extends RestApiServiceService<PagedApiResponse<Utente[]>, Utente> {

}
