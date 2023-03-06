import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ItemsRoutingModule } from './items-routing.module';
import { ItemListComponent } from './item-list/item-list.component';

// Material
import { MatTableModule } from "@angular/material/table";


@NgModule({
  declarations: [
    ItemListComponent
  ],
  imports: [
    CommonModule,
    ItemsRoutingModule,

    // Material
    MatTableModule
  ]
})
export class ItemsModule { }
