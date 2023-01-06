import { Injectable } from '@angular/core';
import { PagedApiResponse } from 'src/app/models/api-response-models';
import { Materia } from 'src/app/models/materia';
import { RestApiServiceService } from './rest-api-service.service';

@Injectable({
  providedIn: 'root'
})
export class MateriaApiService extends RestApiServiceService<PagedApiResponse<Materia[]>, Materia> {

}
