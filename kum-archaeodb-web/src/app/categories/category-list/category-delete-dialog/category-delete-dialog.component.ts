import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from "@angular/material/dialog";

@Component({
  selector: 'app-category-delete-dialog',
  templateUrl: './category-delete-dialog.component.html',
  styleUrls: ['./category-delete-dialog.component.css']
})
export class CategoryDeleteDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: string) {}
}
