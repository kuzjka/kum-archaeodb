import {Component, OnInit, ViewChild} from '@angular/core';
import {Item} from "../items.model";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ItemsService} from "../../items.service";
import {MatSort, Sort} from "@angular/material/sort";

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
  categories: string[] = [];
  totalItems: number = 0;
  totalPages: number = 0;
  pageSize: number = 25;
  columnsToDisplay = ['pointNumber', 'name', 'category', 'dimensions', 'latitude', 'longitude'];
  items!: Item[];
  pages!: number[];
  selectedCategories: string[] = [];
  currentPage: number = 0;
  currentSort: string = 'pointNumber';
  currentOrder: string = 'asc';
  constructor(private service: ItemsService) {
  }
  @ViewChild(MatSort) sort?: MatSort;
  public handleFilter(category: string) {
    if (this.selectedCategories.includes(category)) {
      let index = this.selectedCategories.indexOf(category);
      this.selectedCategories.splice(index);
    } else {
      this.selectedCategories.push(category);
    }
    this.getItems(this.currentPage, this.pageSize, this.selectedCategories, this.currentSort, this.currentOrder);
  }
  public handleSort(sortState: Sort) {
    this.currentSort = sortState.active;
    this.currentOrder = sortState.direction;
    this.getItems(this.currentPage, this.pageSize, this.selectedCategories, this.currentSort, this.currentOrder);
  }
  public handlePage(e: any) {
    this.currentPage = e.pageIndex;
    this.pageSize = e.pageSize;
    this.getItems(this.currentPage, this.pageSize, this.selectedCategories, this.currentSort, this.currentOrder);
  }
  getCategories() {
    this.service.getCategoryNames().subscribe(data => {
      this.categories = data;
    })
  }
  getItems(page: number, size: number, categories: string[], sort: string, order: string) {
    this.service.getItems(page, size, categories, sort, order).subscribe(data => {
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
    this.getCategories();
    this.getItems(this.currentPage, this.pageSize, this.selectedCategories, this.currentSort, this.currentOrder);
  }
}
