import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  formData = {
    name: '',
    username: '',
    email: '',
    password: '',
  };

  errorMessage: string = '';
  successMessage: string = '';

  constructor(private authService: AuthService, private router: Router, private http: HttpClient) { }

  onRegister() {
    if (!this.formData.name || !this.formData.username || !this.formData.email || !this.formData.password) {
      alert('Por favor, completa todos los campos.');
      return;
    }

    this.http.post('/api/v1/auth/register', this.formData)
      .subscribe({
        next: (response) => {
          console.log('Registro exitoso:', response);
          alert('Usuario registrado correctamente. Ahora puedes iniciar sesión.');
        },
        error: (error) => {
          console.error('Error al registrar usuario:', error);
          alert('Hubo un error al registrar el usuario.');
        },
      });
  }
}
