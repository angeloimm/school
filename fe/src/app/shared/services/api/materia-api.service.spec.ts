import { TestBed } from '@angular/core/testing';

import { MateriaApiService } from './materia-api.service';

describe('MateriaApiService', () => {
  let service: MateriaApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MateriaApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
