import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryEditComponent } from './category-edit.component';
import { MatButtonModule } from "@angular/material/button";
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { NoopAnimationsModule } from "@angular/platform-browser/animations";

describe('CategoryEditComponent', () => {
  let component: CategoryEditComponent;
  let fixture: ComponentFixture<CategoryEditComponent>;

  let matDialogRefSpy: jasmine.SpyObj<MatDialogRef<CategoryEditComponent>>
  const TEST_DIALOG_DATA = {
    new: true, category: {
      name: '',
      filters: []
    }
  };

  beforeEach(async () => {
    matDialogRefSpy = jasmine.createSpyObj('MatDialogRef', ['close']);

    await TestBed.configureTestingModule({
      declarations: [ CategoryEditComponent ],
      imports: [
        MatButtonModule,
        MatDialogModule,
        MatInputModule,
        MatFormFieldModule,
        NoopAnimationsModule
      ],
      providers: [
        { provide: MatDialogRef, useValue: matDialogRefSpy },
        { provide: MAT_DIALOG_DATA, useValue: TEST_DIALOG_DATA }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
