import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemListComponent } from './item-list.component';
import { ItemsService } from "../../items.service";
import { MatTableModule } from "@angular/material/table";
import { MatButtonModule } from "@angular/material/button";
import { of } from "rxjs";
import { Bullet, Item } from "../items.model";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatSortModule } from "@angular/material/sort";
import { MatChipsModule } from "@angular/material/chips";
import { MatMenuModule } from "@angular/material/menu";
import { TestbedHarnessEnvironment } from "@angular/cdk/testing/testbed";
import { MatButtonHarness } from "@angular/material/button/testing";
import { MatMenuItemHarness } from "@angular/material/menu/testing";
import { MatChipOptionHarness } from "@angular/material/chips/testing";

describe('ItemListComponent', () => {
  let component: ItemListComponent;
  let fixture: ComponentFixture<ItemListComponent>;

  let itemsServiceSpy: jasmine.SpyObj<ItemsService>;

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

  beforeEach(async () => {
    itemsServiceSpy = jasmine.createSpyObj('ItemsService', [
      'getItems',
      'getCategoryNames',
      'exportItems',
      'exportBullets'
    ]);
    itemsServiceSpy.getItems.and.returnValue(of({
      totalPages: 2,
      totalCount: 4,
      content: TEST_DATA
    }));
    itemsServiceSpy.getCategoryNames.and.returnValue(of(['Category 1', 'Category 2']));

    await TestBed.configureTestingModule({
      declarations: [ ItemListComponent ],
      imports: [
        NoopAnimationsModule,

        MatButtonModule,
        MatChipsModule,
        MatMenuModule,
        MatPaginatorModule,
        MatSortModule,
        MatTableModule
      ],
      providers: [
        { provide: ItemsService, useValue: itemsServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ItemListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should export items no filters', async () => {
    const loader = TestbedHarnessEnvironment.loader(fixture);
    const rootLoader = TestbedHarnessEnvironment.documentRootLoader(fixture);

    const exportBtn = await loader.getHarness(MatButtonHarness.with({ text: RegExp('Експортувати') }));
    await exportBtn.click();

    /* use root loader, because menu overlay is outside the component root element */
    const exportItemsBtn = await rootLoader.getHarness(MatMenuItemHarness.with({ text: 'Всі знахідки' }));
    await exportItemsBtn.click();

    expect(itemsServiceSpy.exportItems).toHaveBeenCalledWith(jasmine.falsy(), jasmine.empty());
  });

  it('should export items filtered by categories', async() => {
    const loader = TestbedHarnessEnvironment.loader(fixture);
    const rootLoader = TestbedHarnessEnvironment.documentRootLoader(fixture);

    const categoryChip = await loader.getHarness(MatChipOptionHarness.with({ text: 'Category 1'}));
    await categoryChip.select();

    const exportBtn = await loader.getHarness(MatButtonHarness.with({ text: RegExp('Експортувати') }));
    await exportBtn.click();

    /* use root loader, because menu overlay is outside the component root element */
    const exportItemsBtn = await rootLoader.getHarness(MatMenuItemHarness.with({ text: 'Всі знахідки' }));
    await exportItemsBtn.click();

    expect(itemsServiceSpy.exportItems).toHaveBeenCalledWith(jasmine.falsy(), ['Category 1']);
  });

  it('should export bullets', async () => {
    const loader = TestbedHarnessEnvironment.loader(fixture);
    const rootLoader = TestbedHarnessEnvironment.documentRootLoader(fixture);

    const exportBtn = await loader.getHarness(MatButtonHarness.with({ text: RegExp('Експортувати') }));
    await exportBtn.click();

    /* use root loader, because menu overlay is outside the component root element */
    const exportBulletsBtn = await rootLoader.getHarness(MatMenuItemHarness.with({ text: 'Всі кулі' }));
    await exportBulletsBtn.click();

    expect(itemsServiceSpy.exportBullets).toHaveBeenCalledWith();
  });
});
