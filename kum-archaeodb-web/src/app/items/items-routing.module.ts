import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemListComponent } from "./item-list/item-list.component";
import { ItemsImportComponent } from "./items-import/items-import.component";

const routes: Routes = [
  {
    path: '',
    component: ItemListComponent
  },
  {
    path: 'import',
    component: ItemsImportComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ItemsRoutingModule { }
