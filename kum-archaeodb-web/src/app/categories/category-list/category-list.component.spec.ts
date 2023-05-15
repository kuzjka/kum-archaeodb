import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryListComponent } from './category-list.component';
import { ItemsService } from "../../items.service";
import { of } from "rxjs";
import { Category } from "../categories.model";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatTableModule } from "@angular/material/table";
import { MatDialog } from "@angular/material/dialog";

describe('CategoryListComponent', () => {
  let component: CategoryListComponent;
  let fixture: ComponentFixture<CategoryListComponent>;

  let itemsServiceSpy: jasmine.SpyObj<ItemsService>;
  let matDialogSpy: jasmine.SpyObj<MatDialog>;

  const TEST_CATEGORIES: Array<Category> = [
    {
      id: 1,
      name: 'Кулі',
      filters: ['Куля']
    },
    {
      id: 2,
      name: 'Спорядження вершника',
      filters: ['Вухналь', 'Підкова']
    },
    {
      id: 3,
      name: 'Вогнепальна зброя',
      filters: ['Мушкет']
    }
  ];

  beforeEach(async () => {
    matDialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    itemsServiceSpy = jasmine.createSpyObj('ItemsService', ['getCategories', 'addCategory',
      'editCategory', 'deleteCategory']);
    itemsServiceSpy.getCategories.and.returnValue(of(TEST_CATEGORIES));

    await TestBed.configureTestingModule({
      declarations: [ CategoryListComponent ],
      imports: [
        MatButtonModule,
        MatIconModule,
        MatTableModule
      ],
      providers: [
        { provide: ItemsService, useValue: itemsServiceSpy },
        { provide: MatDialog, useValue: matDialogSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
