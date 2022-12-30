import { Injectable } from '@angular/core';
import { ApiResponse } from 'src/app/models/api-response-models';
import { RestApiServiceService } from './rest-api-service.service';

@Injectable({
  providedIn: 'root'
})
export class InitDbServiceService extends RestApiServiceService<ApiResponse<boolean>, void> {

  
}
