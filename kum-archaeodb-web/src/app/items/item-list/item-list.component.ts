import {Component, OnInit, ViewChild} from '@angular/core';
import {Item} from "../items.model";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ItemsService} from "../../items.service";
import {MatPaginator} from "@angular/material/paginator";


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
  pageSize: number = 2;
  columnsToDisplay = ['pointNumber', 'name', 'category', 'dimensions', 'latitude', 'longitude'];
  items!: Item[];
  pages!: number[];
  currentPage: number = 0;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private service: ItemsService) {
  }

  public handlePage(e: any) {
    this.currentPage = e.pageIndex;
    this.pageSize = e.pageSize;
    this.getItems(this.currentPage, this.pageSize);

  }

  getItems(page: number, size: number) {
    this.service.getItems(page, size).subscribe(data => {
      this.items = data.content;
      this.totalItems = data.totalCount;
      this.totalPages = data.totalPages;
      this.getPages();
      this.currentPage = 0;
    })
  }
  getPages() {
    this.pages = [];
    for (let i = 0; i < this.totalPages; i++) {
      this.pages.push(i);
    }
  }
  ngOnInit(): void {
    this.getItems(this.currentPage, this.pageSize);
  }
}
