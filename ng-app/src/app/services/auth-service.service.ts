import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { AuthResponse } from '../models/AuthResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthServiceService {

  private accessToken: any = null;

  constructor(private http: HttpClient) {}

  setToken(token: string) {
    this.accessToken = token;
    localStorage.setItem('token', token);
  }

  isAuthenticate(): boolean {
    return this.getToken() != null;
  }

  getToken(): string {
    if (!this.accessToken) {
      this.accessToken = localStorage.getItem('token');
    }
    return this.accessToken;
  }

  getHeaderRequest() : string{
    return `Bearer ${this.getToken()}`;
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`/api/system-development/v1/user/login`, {
        email,
        password
    });
  }
}
