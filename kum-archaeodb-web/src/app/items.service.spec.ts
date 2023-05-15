import { TestBed } from '@angular/core/testing';

import { ItemsService } from './items.service';
import { HttpClient } from "@angular/common/http";

describe('ItemsService', () => {
  let service: ItemsService;
  let httpClientSpy: jasmine.SpyObj<HttpClient>;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);
    service = new ItemsService(httpClientSpy);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
