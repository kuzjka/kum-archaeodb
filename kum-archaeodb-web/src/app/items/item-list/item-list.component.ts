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
  pages!: number[];

  constructor(private service: ItemsService) {
  }
  getItems(page:number) {
    this.service.getItems(page).subscribe(data => {
      this.items = data.content;
      this.totalItems = data.totalCount;
      this.totalPages = data.totalPages;
      this.getPages();
    })
  }
  getPages() {
    this.pages = [];
    for (let i = 0; i < this.totalPages; i++) {

      this.pages.push(i);
    }
  }
  ngOnInit(): void {
    this.getItems(0);
  }
}
