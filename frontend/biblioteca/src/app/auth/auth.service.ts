import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError, tap, timeout } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = '/v1/auth';

  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${environment.apiUrl}${this.apiUrl}/login`, { username, password })/*.pipe(
      tap((response: any) => {
        if (response.token) {
          sessionStorage.setItem('token', response.token); // Usar cookies si es más seguro
        }
      }),
      catchError((err) => {
        console.error('Login error:', err);
        return throwError(err);
      })
    );*/
  }

  register(userData: any): Observable<any> {
    return this.http.post(`${environment.apiUrl}${this.apiUrl}/register`, userData, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    }).pipe(
      timeout(10000), // Ajusta el tiempo de espera en milisegundos
      catchError((err) => {
        console.error('Error:', err);
        return throwError(err);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }
}