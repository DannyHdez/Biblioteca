import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BooksService {
  private apiUrl = '/v2/books';

  constructor(private http: HttpClient) { }

  getBooks(): Observable<any> {
    return this.http.get(`${environment.apiUrl}${this.apiUrl}`);
  }

  getBookById(id: number): Observable<any> {
    return this.http.get(`${environment.apiUrl}${this.apiUrl}/${id}`);
  }

  createBook(bookData: any): Observable<any> {
    return this.http.post(`${environment.apiUrl}${this.apiUrl}/createBook`, bookData);
  }
}
