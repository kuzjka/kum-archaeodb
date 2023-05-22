import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Category} from "./categories/categories.model";
import {ItemParsingRequestDto, PageDto, ParsedItem} from "./items/items.model";


@Injectable({
  providedIn: 'root'
})
export class ItemsService {
  constructor(private http: HttpClient) {
  }

  getItems(page: number, size: number, categories: string[], sort: string, order: string): Observable<PageDto> {

    return this.http.get<PageDto>(`/api/items?page=${page}&size=${size}&categories=${categories}&sort=${sort}&order=${order}`);
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>('/api/categories');
  }

  getCategoryNames(): Observable<string[]> {
    return this.http.get<string[]>('/api/categoryNames');
  }

  addCategory(category: Category): Observable<any> {
    let name = category.name;
    let filters = category.filters;
    let data = {
      name: name,
      filters: filters
    }
    return this.http.post<any>('/api/categories', data);
  }

  parse(data: ItemParsingRequestDto): Observable<ParsedItem[]> {
    return this.http.post<ParsedItem[]>('/api/items/parse', data);
  }

  addParsed(data: ParsedItem[]): Observable<any> {
    return this.http.post<any>('/api/items/addParsed', data);
  }

  editCategory(category: Category): Observable<any> {
    let id = category.id;
    let name = category.name;
    let filters = category.filters;
    let data = {
      id: id,
      name: name,
      filters: filters
    }
    return this.http.put<any>('/api/categories', data);
  }

  deleteCategory(id: number): Observable<any> {
    return this.http.delete<any>('/api/categories/' + id);
  }
}
