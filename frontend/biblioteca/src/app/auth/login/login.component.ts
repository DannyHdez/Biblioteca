import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  credentials = {
    username: '',
    password: '',
  };

  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.authService.login(this.credentials.username, this.credentials.password).subscribe(
      (response: any) => {
        localStorage.setItem('token', response.token);
        this.router.navigate(['/books']);
      },
      (error) => {
        this.errorMessage = 'Usuario o contraseña incorrectos.';
      }
    );
  }
}
