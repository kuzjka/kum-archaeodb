import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryDeleteDialogComponent } from './category-delete-dialog.component';
import { MatButtonModule } from "@angular/material/button";
import { MAT_DIALOG_DATA, MatDialogModule } from "@angular/material/dialog";

describe('CategoryDeleteDialogComponent', () => {
  let component: CategoryDeleteDialogComponent;
  let fixture: ComponentFixture<CategoryDeleteDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoryDeleteDialogComponent ],
      imports: [
        MatButtonModule,
        MatDialogModule
      ],
      providers: [
        {
          provide: MAT_DIALOG_DATA,
          useValue: 'dialog data'
        }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryDeleteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
