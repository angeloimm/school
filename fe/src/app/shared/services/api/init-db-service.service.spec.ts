import { TestBed } from '@angular/core/testing';

import { InitDbServiceService } from './init-db-service.service';

describe('InitDbServiceService', () => {
  let service: InitDbServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InitDbServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
