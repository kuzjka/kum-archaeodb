import { Component, OnInit } from '@angular/core';
import { Bullet, Item } from "../items.model";
import { CollectionViewer, DataSource, ListRange } from "@angular/cdk/collections";
import { Observable, of } from "rxjs";

const TEST_DATA: Array<Item | Bullet> = [
  {
    name: 'Куля свинцева з хвостиком',
    year: 2021,
    pointNumber: '1',
    dimensions: '17х17х20',
    weight: 26,
    caliber: 17,
    location: {
      latitude: 12.345,
      longitude: 23.456
    },
    gpsPoint: "591",
    remarks: "Маршрут 1"
  },
  {
    pointNumber: "103",
    name: "Вухналь залізний",
    year: 2021,
    dimensions: "довж - 30; шир макс - 8",
    location: {
      latitude: 34.456,
      longitude: 11.223334
    },
    remarks: "Выльний пошук, Південний сектор"
  },
  {
    pointNumber: "134",
    name: "Сіканець залізний",
    year: 2021,
    dimensions: "52х35х22",
    weight: 114,
    location: {
      latitude: 11.543,
      longitude: 33.3214
    },
    gpsPoint: "93",
    remarks: "Вільний пошук, Трикутний ліс"
  },
  {
    pointNumber: "362/1",
    name: "Монета-солід, гаманець №1",
    location: {
      latitude: 22.3333,
      longitude: 33.4444
    },
    gpsPoint: "23юб",
    remarks: "Верх верху"
  }
];

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent {
  totalItems: number = 4;
  totalPages: number = 1;

  items = TEST_DATA;
  itemsDataSource = new ItemsDataSource();
  columnsToDisplay = ['pointNumber', 'name', 'dimensions', 'latitude', 'longitude'];
}

class ItemsDataSource extends DataSource<Item> {

  connect(collectionViewer: CollectionViewer): Observable<Item[]> {
    return of(TEST_DATA);
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }
}
