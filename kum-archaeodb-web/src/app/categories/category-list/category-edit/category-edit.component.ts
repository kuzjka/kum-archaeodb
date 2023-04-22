import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Category } from "../../categories.model";

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.css']
})
export class CategoryEditComponent {
  title: string;

  categoryForm = new FormGroup({
    name: new FormControl<string>(this.data.category.name, {nonNullable: true, validators: Validators.required}),
    filters: new FormControl(this.data.category.filters.join(', '))
  });

  constructor(
    private dialogRef: MatDialogRef<CategoryEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private fb: FormBuilder) {
    this.title = data.new ? "Створення категорії" : "Змінення категорії";
  }

  onClose() {
    this.dialogRef.close();
  }

  submit() {
    this.data.category.name = this.categoryForm.controls.name.value;
    const filtersValue = this.categoryForm.value.filters;
    this.data.category.filters = filtersValue?.split(new RegExp("[\\s,]+")) ?? [];
    this.dialogRef.close(this.data.category);
  }
}

interface DialogData {
  new: boolean,
  category: Category
}
