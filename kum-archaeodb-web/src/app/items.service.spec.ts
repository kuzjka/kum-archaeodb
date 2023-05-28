import { TestBed } from '@angular/core/testing';

import { ItemsService } from './items.service';
import { HttpClient } from "@angular/common/http";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { map } from "rxjs";

describe('ItemsService', () => {
  let service: ItemsService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ]
    });

    service = TestBed.inject(ItemsService);
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call exportItems with no filters', () => {
    const testBlob = new Blob(['abcd']);

    service.exportItems().subscribe(data => {
      data.text().then(text => {
        expect(text).toBe('abcd');
      }).catch(e => fail(e));
    });

    const req = httpTestingController.expectOne('/api/exportItems');

    expect(req.request.method).toEqual('POST');
    expect(req.request.body.ids).toBeFalsy();
    expect(req.request.body.filters).toBeFalsy();

    req.flush(testBlob);

    httpTestingController.verify();
  });

  it('should call exportItems with filters', () => {
    const testBlob = new Blob(['abcd']);

    service.exportItems([1, 2, 3], ['Cat1', 'Cat2']).subscribe(data => {
      data.text().then(text => {
        expect(text).toBe('abcd');
      }).catch(e => fail(e));
    });

    const req = httpTestingController.expectOne('/api/exportItems');

    expect(req.request.method).toEqual('POST');
    expect(req.request.body.ids).toEqual([1, 2, 3]);
    expect(req.request.body.categories).toEqual(['Cat1', 'Cat2']);

    req.flush(testBlob);

    httpTestingController.verify();
  });

  it('should call exportBullets with no filters', () => {
    const testBlob = new Blob(['abcd']);

    service.exportBullets().subscribe(data => {
      data.text().then(text => {
        expect(text).toBe('abcd');
      }).catch(e => fail(e));
    });

    const req = httpTestingController.expectOne('/api/exportBullets');

    expect(req.request.method).toEqual('POST');
    expect(req.request.body.ids).toBeFalsy();

    req.flush(testBlob);

    httpTestingController.verify();
  });

  it('should call exportBullets with filters', () => {
    const testBlob = new Blob(['abcd']);

    service.exportBullets([2, 3, 4]).subscribe(data => {
      data.text().then(text => {
        expect(text).toBe('abcd');
      }).catch(e => fail(e));
    });

    const req = httpTestingController.expectOne('/api/exportBullets');

    expect(req.request.method).toEqual('POST');
    expect(req.request.body.ids).toEqual([2, 3, 4]);

    req.flush(testBlob);

    httpTestingController.verify();
  });
});
