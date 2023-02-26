import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ItemsRoutingModule } from './items-routing.module';
import { ItemListComponent } from './item-list/item-list.component';


@NgModule({
  declarations: [
    ItemListComponent
  ],
  imports: [
    CommonModule,
    ItemsRoutingModule
  ]
})
export class ItemsModule { }
