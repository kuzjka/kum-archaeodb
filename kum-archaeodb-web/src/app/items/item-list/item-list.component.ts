import { Component, OnInit } from '@angular/core';
import { Bullet, Item } from "../items.model";
import { CollectionViewer, DataSource, ListRange } from "@angular/cdk/collections";
import { Observable, of } from "rxjs";
import { animate, state, style, transition, trigger } from "@angular/animations";
import { MatTableDataSource } from "@angular/material/table";

const TEST_DATA: Array<Item | Bullet> = [
  {
    name: 'Куля свинцева з хвостиком',
    category: 'Кулі',
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
    category: 'Спорядження коней',
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
    category: 'Артилерійський боєприпас',
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
    category: 'Монети',
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
  styleUrls: ['./item-list.component.css'],
  animations: [
    trigger('tableExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ]),
  ],
})
export class ItemListComponent {
  totalItems: number = 4;
  totalPages: number = 1;

  columnsToDisplay = ['pointNumber', 'name', 'category', 'dimensions', 'latitude', 'longitude'];

  items = TEST_DATA.map(item => ({
    ...item,
    isExpanded: false
  }));
  tableDataSource = new MatTableDataSource(this.items);
}
