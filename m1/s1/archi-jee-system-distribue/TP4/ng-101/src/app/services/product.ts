import { Service } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../model/product';
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private backendHost = 'http://localhost:8083';

  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<any> {
    return this.http.get(`${this.backendHost}/products`);
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.backendHost}/products/${id}`);
  }
}
