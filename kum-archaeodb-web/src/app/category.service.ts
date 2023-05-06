import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Category} from "./categories/categories.model";


@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  constructor(private http: HttpClient) {
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>('/api/categories');
  }

  addCategory(category: Category): Observable<any> {
    let id = category.id;
    let name = category.name;
    let filters = category.filters;
    let data = {
      name: name,
      filters: filters
    }
    return this.http.post<any>('/api/categories', data);
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