import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="login-container">
      <div class="login-card">
        <h2>🔐 Iniciar Sesión</h2>
        <form (ngSubmit)="onLogin()">
          <div class="form-group">
            <label>Correo Electrónico</label>
            <input type="email" [(ngModel)]="correo" name="correo" required>
          </div>
          <div class="form-group">
            <label>Contraseña</label>
            <input type="password" [(ngModel)]="password" name="password" required>
          </div>
          <button type="submit" [disabled]="loading">{{ loading ? 'Cargando...' : 'Ingresar' }}</button>
          <p *ngIf="error" class="error">{{ error }}</p>
        </form>
      </div>
    </div>
  `,
  styles: [`
    .login-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 80vh;
    }
    .login-card {
      background: white;
      padding: 3rem;
      border-radius: 10px;
      box-shadow: 0 5px 20px rgba(0,0,0,0.2);
      width: 400px;
    }
    h2 {
      text-align: center;
      color: #667eea;
      margin-bottom: 2rem;
    }
    .form-group {
      margin-bottom: 1.5rem;
    }
    label {
      display: block;
      margin-bottom: 0.5rem;
      color: #555;
      font-weight: 500;
    }
    input {
      width: 100%;
      padding: 0.75rem;
      border: 1px solid #ddd;
      border-radius: 5px;
      font-size: 1rem;
    }
    button {
      width: 100%;
      padding: 0.75rem;
      background: #667eea;
      color: white;
      border: none;
      border-radius: 5px;
      font-size: 1rem;
      font-weight: 600;
      cursor: pointer;
      transition: background 0.3s;
    }
    button:hover:not(:disabled) {
      background: #5568d3;
    }
    button:disabled {
      background: #ccc;
    }
    .error {
      color: red;
      margin-top: 1rem;
    }
  `]
})
export class LoginComponent {
  correo = '';
  password = '';
  loading = false;
  error = '';

  constructor(
    private apiService: ApiService,
    private router: Router
  ) {}

  onLogin() {
    this.loading = true;
    this.error = '';
    
    this.apiService.login(this.correo, this.password).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('user', JSON.stringify(response));
        this.router.navigate(['/rutas']);
      },
      error: (err) => {
        this.error = 'Credenciales incorrectas';
        this.loading = false;
      }
    });
  }
}

