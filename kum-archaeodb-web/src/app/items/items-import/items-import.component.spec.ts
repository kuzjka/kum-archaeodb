import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemsImportComponent } from './items-import.component';
import { MatStepperModule } from "@angular/material/stepper";
import { MatButtonModule } from "@angular/material/button";
import { MatRadioModule } from "@angular/material/radio";
import { MatListModule } from "@angular/material/list";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatTableModule } from "@angular/material/table";
import { MatSnackBar, MatSnackBarModule } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { ItemsService } from "../../items.service";
import { ParsedItem } from "../items.model";
import { of } from "rxjs";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";

describe('ItemsImportComponent', () => {
  let component: ItemsImportComponent;
  let fixture: ComponentFixture<ItemsImportComponent>;

  let snackBarSpy: jasmine.SpyObj<MatSnackBar>;
  let routerSpy: jasmine.SpyObj<Router>
  let itemsServiceSpy: jasmine.SpyObj<ItemsService>

  const TEST_PARSED_DATA: Array<ParsedItem> = [
    {
      name: "Куля свинцева",
      pointNumber: "145",
      location: {latitude: 12.34, longitude: 23.45},
      hectare: 4,
      dimensions: "12x13x14",
      weight: 17,
      remarks: "remarks",
      gpsPoint: "234н",
      category: "Кулі",
      caliber: 13,
      caliberApproximate: false,
      deformation: "NONE",
      numberExists: false,
      categoryAutodetected: true,
      caliberAutodetected: true,
      deformationAutodetected: false,
      hectareAutodetected: true,
      save: true
    },
    {
      name: "Куля свинцева деформована",
      pointNumber: "149",
      location: {latitude: 11.22, longitude: 22.33},
      dimensions: "15x16x11",
      weight: 17,
      remarks: "remarks",
      gpsPoint: "11",
      category: "Кулі",
      caliber: 16,
      caliberApproximate: true,
      deformation: "LIGHT",
      numberExists: true,
      categoryAutodetected: true,
      caliberAutodetected: false,
      deformationAutodetected: true,
      hectareAutodetected: false,
      save: true
    },
    {
      name: "Вухналь залізний",
      pointNumber: "236/2",
      location: {latitude: 21.43, longitude: 32.54},
      hectare: 3,
      dimensions: "42х12",
      weight: 27,
      remarks: "remarks",
      gpsPoint: "432в",
      category: "Спорядження вершника",
      caliber: -1,
      caliberApproximate: false,
      deformation: "NONE",
      numberExists: false,
      categoryAutodetected: true,
      caliberAutodetected: false,
      deformationAutodetected: false,
      hectareAutodetected: true,
      save: true
    }
  ];

  const TEST_CATEGORIES = ["Кулі", "Спорядження вершника", "Холодна зброя"];

  beforeEach(async () => {
    snackBarSpy = jasmine.createSpyObj('SnackBar', ['open']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    itemsServiceSpy = jasmine.createSpyObj('ItemsService', ['parse', 'addParsed', 'getCategoryNames']);
    itemsServiceSpy.parse.and.returnValue(of(TEST_PARSED_DATA));
    itemsServiceSpy.getCategoryNames.and.returnValue(of(TEST_CATEGORIES));

    await TestBed.configureTestingModule({
      declarations: [ ItemsImportComponent ],
      imports: [
        MatButtonModule,
        MatListModule,
        MatProgressSpinnerModule,
        MatSnackBarModule,
        MatStepperModule,
        MatTableModule,
        MatRadioModule,
        NoopAnimationsModule
      ],
      providers: [
        { provide: MatSnackBar, useValue: snackBarSpy },
        { provide: Router, useValue: routerSpy },
        { provide: ItemsService, useValue: itemsServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ItemsImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
