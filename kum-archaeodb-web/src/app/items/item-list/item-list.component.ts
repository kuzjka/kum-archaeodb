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

  ngOnInit(): void {
    this.getCategories();
    this.getItems(this.currentPage, this.pageSize, this.selectedCategories, this.currentSort, this.currentOrder);
  }

  public handleFilter(category: string) {
    if (this.selectedCategories.length == 0) {
      this.selectedCategories.push(category);
    } else if (this.selectedCategories.length > 0) {

      if (this.selectedCategories.includes(category)) {
        let index = this.selectedCategories.indexOf(category);
        this.selectedCategories.splice(index, 1);
      } else if (!this.selectedCategories.includes(category)) {
        this.selectedCategories.push(category);
      }
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
    });
  }

  getItems(page: number, size: number, categories: string[], sort: string, order: string) {
    this.service.getItems(page, size, categories, sort, order).subscribe(data => {
      this.items = data.content;
      this.totalItems = data.totalCount;
      this.totalPages = data.totalPages;
      this.getPages();
      this.currentPage = 0;
    });
  }

  getPages() {
    this.pages = [];
    for (let i = 0; i < this.totalPages; i++) {
      this.pages.push(i);
    }
  }

  /**
   * Requests for CSV with items. If items are filtered by categories, the result is also filtered.
   */
  exportItems() {
    this.service.exportItems(undefined, this.selectedCategories).subscribe(blob => {
      this.downloadBlob(blob, 'items.csv');
    });
  }

  /**
   * Requests for CSV with bullets.
   */
  exportBullets() {
    this.service.exportBullets().subscribe( blob => {
      this.downloadBlob(blob, 'bullets.csv');
    });
  }

  /**
   * Creates a temporary link for BLOB and clicks it to download.
   * @param blob      BLOB to download
   * @param filename  File name
   */
  downloadBlob(blob: Blob, filename: string) {
    const link = document.createElement('a');
    link.setAttribute('style', 'display: none');
    document.body.appendChild(link);

    const url = window.URL.createObjectURL(blob);
    link.href = url;
    link.download = filename;
    link.click();
    window.URL.revokeObjectURL(url);
    link.parentNode!.removeChild(link);
  }
}
