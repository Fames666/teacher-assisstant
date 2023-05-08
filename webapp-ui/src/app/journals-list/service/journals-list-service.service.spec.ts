import { TestBed } from '@angular/core/testing';

import { JournalsListService } from './journals-list-service.service';

describe('JournalsListServiceService', () => {
  let service: JournalsListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JournalsListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
