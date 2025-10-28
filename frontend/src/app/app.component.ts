import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  template: `
    <div class="app-container">
      <nav class="navbar">
        <div class="nav-brand">
          <h1>🚌 TransitWeb</h1>
        </div>
        <div class="nav-links">
          <a routerLink="/login" *ngIf="!isAuthenticated">Login</a>
          <a routerLink="/rutas">Rutas</a>
          <a routerLink="/conductores">Conductores</a>
          <a routerLink="/buses">Buses</a>
          <a routerLink="/login" (click)="logout()" *ngIf="isAuthenticated">Salir</a>
        </div>
      </nav>
      <main class="main-content">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    .app-container {
      min-height: 100vh;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    }
    .navbar {
      background: #fff;
      padding: 1rem 2rem;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .nav-brand h1 {
      margin: 0;
      color: #667eea;
    }
    .nav-links {
      display: flex;
      gap: 2rem;
    }
    .nav-links a {
      text-decoration: none;
      color: #333;
      font-weight: 500;
      transition: color 0.3s;
    }
    .nav-links a:hover {
      color: #667eea;
    }
    .main-content {
      padding: 2rem;
    }
  `]
})
export class AppComponent {
  isAuthenticated = false;

  logout() {
    localStorage.removeItem('token');
    this.isAuthenticated = false;
  }
}

