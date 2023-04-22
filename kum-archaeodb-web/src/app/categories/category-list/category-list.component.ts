import { Component } from '@angular/core';
import { Category } from "../categories.model";
import { MatDialog } from "@angular/material/dialog";
import { CategoryEditComponent } from "./category-edit/category-edit.component";
import { CategoryDeleteDialogComponent } from "./category-delete-dialog/category-delete-dialog.component";
import { MatTableDataSource } from "@angular/material/table";

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent {
  _mock_backend_ids = 100;

  categories: Array<Category> = [
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

  categoriesSource: MatTableDataSource<Category> = new MatTableDataSource<Category>(this.categories);

  tableColumns = ['name', 'filters', 'actions'];


  constructor(public dialog: MatDialog) {}

  addCategory() {
    const dialogRef = this.dialog.open(CategoryEditComponent, {
      width: '600px',
      data: {
        new: true,
        category: { name: '', filters: []}
      }
    });

    dialogRef.afterClosed().subscribe(category => {
      if (category) {
        console.log("Add category: \n" + JSON.stringify(category, null, 2));
        // backend should return ID
        category.id = this._mock_backend_ids++;

        this.categories.push(category);
        this.categoriesSource.data = this.categories;
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
        this.categories.splice(index, 1);
        this.categoriesSource.data = this.categories;
        console.log("Delete category: \n" + JSON.stringify(category, null, 2));
        console.log("Categories include: " + this.categories.map(c => c.name).join(", "));
      }
    });
  }
}
