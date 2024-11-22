import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/v2/users';

  constructor(private http: HttpClient) {}

  getUserProfile(): Observable<any> {
    return this.http.get(`${this.apiUrl}/me`);
  }

  updateUserProfile(userData: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/me`, userData);
  }

  changePassword(passwordData: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/me/change-password`, passwordData);
  }

  disableAccount(): Observable<any> {
    return this.http.put(`${this.apiUrl}/disable-account`, {});
  }
}
