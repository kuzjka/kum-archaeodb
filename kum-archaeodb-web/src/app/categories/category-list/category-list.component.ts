import {Component, OnInit} from '@angular/core';
import {Category} from "../categories.model";
import {MatDialog} from "@angular/material/dialog";
import {CategoryEditComponent} from "./category-edit/category-edit.component";
import {CategoryDeleteDialogComponent} from "./category-delete-dialog/category-delete-dialog.component";
import {CategoryService} from "../../category.service";


@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css'],
  providers: [CategoryService]
})
export class CategoryListComponent implements OnInit {
  categories!: Category  [];
  tableColumns = ['name', 'filters', 'actions'];

  constructor(public dialog: MatDialog, public service: CategoryService) {
  }

  getCategories() {
    this.service.getCategories().subscribe(data => {
      this.categories = data;
    })
  }

  addCategory() {
    const dialogRef = this.dialog.open(CategoryEditComponent, {
      width: '600px',
      data: {
        new: true,
        category: {name: '', filters: []}
      }
    });
    dialogRef.afterClosed().subscribe(category => {
      if (category) {
        console.log("Add category: \n" + JSON.stringify(category, null, 2));
        this.service.addCategory(category).subscribe(data => {
          this.getCategories();
        })
      }
    })
  }

  editCategory(category: Category) {
    const dialogRef = this.dialog.open(CategoryEditComponent, {
      width: '600px',
      data: {
        new: false,
        category: category
      }
    });
    dialogRef.afterClosed().subscribe(category => {
      if (category)
        this.service.editCategory(category).subscribe(data => {
          this.service.getCategories();
        })
      console.log("Update category: \n" + JSON.stringify(category, null, 2));
    })
  }

  deleteCategory(category: Category) {
    const dialogRef = this.dialog.open(CategoryDeleteDialogComponent, {
      width: '400px',
      data: category.name
    });
    dialogRef.afterClosed().subscribe(del => {
      if (del) {
        const index = this.categories.indexOf(category);
        this.service.deleteCategory(category.id).subscribe(data => {
          this.getCategories();
        });
      }
    });
    this.getCategories();
    console.log("Delete category: \n" + JSON.stringify(category, null, 2));
    console.log("Categories include: " + this.categories.map(c => c.name).join(", "));
  }

  ngOnInit(): void {
    this.getCategories();
  }
}
