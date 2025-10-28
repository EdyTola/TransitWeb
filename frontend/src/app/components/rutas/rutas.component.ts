import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';

interface Ruta {
  id?: number;
  nombre: string;
  colorHex: string;
  codigo: string;
}

@Component({
  selector: 'app-rutas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <h2>🗺️ Gestión de Rutas</h2>
      
      <div class="card">
        <h3>Crear Nueva Ruta</h3>
        <form (ngSubmit)="crearRuta()">
          <div class="form-row">
            <div class="form-group">
              <label>Nombre</label>
              <input [(ngModel)]="nuevaRuta.nombre" name="nombre" required>
            </div>
            <div class="form-group">
              <label>Código</label>
              <input [(ngModel)]="nuevaRuta.codigo" name="codigo" required>
            </div>
            <div class="form-group">
              <label>Color (Hex)</label>
              <input type="color" [(ngModel)]="nuevaRuta.colorHex" name="colorHex" required>
            </div>
          </div>
          <button type="submit">Crear Ruta</button>
        </form>
      </div>

      <div class="card">
        <h3>Rutas Existentes</h3>
        <div *ngIf="rutas.length === 0" class="empty">No hay rutas creadas</div>
        <div class="ruta-list">
          <div *ngFor="let ruta of rutas" class="ruta-item">
            <div class="ruta-color" [style.background-color]="ruta.colorHex"></div>
            <div class="ruta-info">
              <strong>{{ ruta.codigo }}</strong> - {{ ruta.nombre }}
            </div>
            <button (click)="eliminarRuta(ruta.id!)" class="btn-danger">Eliminar</button>
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
    .ruta-list {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }
    .ruta-item {
      display: flex;
      align-items: center;
      gap: 1rem;
      padding: 1rem;
      background: #f5f5f5;
      border-radius: 5px;
    }
    .ruta-color {
      width: 50px;
      height: 50px;
      border-radius: 5px;
    }
    .ruta-info {
      flex: 1;
    }
    .btn-danger {
      background: #e74c3c !important;
    }
  `]
})
export class RutasComponent implements OnInit {
  rutas: Ruta[] = [];
  nuevaRuta: Ruta = {
    nombre: '',
    colorHex: '#667eea',
    codigo: ''
  };

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.cargarRutas();
  }

  cargarRutas() {
    this.apiService.getRutas().subscribe(rutas => {
      this.rutas = rutas;
    });
  }

  crearRuta() {
    this.apiService.crearRuta(this.nuevaRuta).subscribe(() => {
      this.cargarRutas();
      this.nuevaRuta = { nombre: '', colorHex: '#667eea', codigo: '' };
    });
  }

  eliminarRuta(id: number) {
    if (confirm('¿Estás seguro de eliminar esta ruta?')) {
      this.apiService.eliminarRuta(id).subscribe(() => {
        this.cargarRutas();
      });
    }
  }
}

