import {Component, OnInit} from '@angular/core';
import {Item} from "../items.model";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatTableDataSource} from "@angular/material/table";
import {ItemsService} from "../../items.service";


@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css'],
  animations: [
    trigger('tableExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ]),
  ],
})
export class ItemListComponent implements OnInit {
  totalItems: number = 0;
  totalPages: number = 0;

  columnsToDisplay = ['pointNumber', 'name', 'category', 'dimensions', 'latitude', 'longitude'];
  items!: Item[];
    constructor(private service: ItemsService) {
  }
  getItems() {
    this.service.getItems().subscribe(data => {
      this.items = data;
      this.totalItems = data.length;
    })
  }
  ngOnInit(): void {
    this.getItems();
  }
}
