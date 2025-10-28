import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';

interface Conductor {
  id: number;
  nombre: string;
  correo: string;
  rol: string;
}

@Component({
  selector: 'app-conductores',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <h2>👨‍✈️ Gestión de Conductores</h2>
      
      <div class="card">
        <h3>Registrar Nuevo Conductor</h3>
        <form (ngSubmit)="registrarConductor()">
          <div class="form-row">
            <div class="form-group">
              <label>Nombre Completo</label>
              <input [(ngModel)]="nuevoConductor.nombre" name="nombre" required>
            </div>
            <div class="form-group">
              <label>Correo</label>
              <input type="email" [(ngModel)]="nuevoConductor.correo" name="correo" required>
            </div>
            <div class="form-group">
              <label>Contraseña</label>
              <input type="password" [(ngModel)]="nuevoConductor.password" name="password" required>
            </div>
          </div>
          <button type="submit">Registrar Conductor</button>
        </form>
      </div>

      <div class="card">
        <h3>Conductores Registrados</h3>
        <div *ngIf="conductores.length === 0" class="empty">No hay conductores registrados</div>
        <div class="list">
          <div *ngFor="let conductor of conductores" class="item">
            <div class="item-info">
              <strong>{{ conductor.nombre }}</strong>
              <span>{{ conductor.correo }}</span>
            </div>
            <button (click)="eliminarConductor(conductor.id)" class="btn-danger">Eliminar</button>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .container {
      max-width: 1200px;
      margin: 0 auto;
    }
    h2 {
      color: white;
      margin-bottom: 2rem;
    }
    .card {
      background: white;
      padding: 2rem;
      border-radius: 10px;
      margin-bottom: 2rem;
      box-shadow: 0 3px 10px rgba(0,0,0,0.2);
    }
    h3 {
      margin-top: 0;
      color: #667eea;
    }
    .form-row {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 1rem;
      margin-bottom: 1rem;
    }
    .form-group {
      display: flex;
      flex-direction: column;
    }
    label {
      margin-bottom: 0.5rem;
      color: #555;
      font-weight: 500;
    }
    input {
      padding: 0.5rem;
      border: 1px solid #ddd;
      border-radius: 5px;
      width: 100%;
    }
    button {
      padding: 0.75rem 1.5rem;
      background: #667eea;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-weight: 600;
    }
    .empty {
      text-align: center;
      color: #999;
      padding: 2rem;
    }
    .list {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }
    .item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 1rem;
      background: #f5f5f5;
      border-radius: 5px;
    }
    .item-info {
      display: flex;
      flex-direction: column;
      gap: 0.25rem;
    }
    .item-info strong {
      color: #333;
    }
    .item-info span {
      color: #666;
      font-size: 0.9rem;
    }
    .btn-danger {
      background: #e74c3c !important;
    }
  `]
})
export class ConductoresComponent implements OnInit {
  conductores: Conductor[] = [];
  nuevoConductor = {
    nombre: '',
    correo: '',
    password: ''
  };

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.cargarConductores();
  }

  cargarConductores() {
    this.apiService.getConductores().subscribe(conductores => {
      this.conductores = conductores;
    });
  }

  registrarConductor() {
    this.apiService.registrarConductor(this.nuevoConductor).subscribe(() => {
      this.cargarConductores();
      this.nuevoConductor = { nombre: '', correo: '', password: '' };
    });
  }

  eliminarConductor(id: number) {
    if (confirm('¿Estás seguro de eliminar este conductor?')) {
      this.apiService.eliminarUsuario(id).subscribe(() => {
        this.cargarConductores();
      });
    }
  }
}

