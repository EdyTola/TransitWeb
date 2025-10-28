import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';

interface Bus {
  id?: number;
  placa: string;
  estado: string;
  tieneRampa: boolean;
}

@Component({
  selector: 'app-buses',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <h2>🚌 Gestión de Buses</h2>
      
      <div class="card">
        <h3>Registrar Nuevo Bus</h3>
        <form (ngSubmit)="crearBus()">
          <div class="form-row">
            <div class="form-group">
              <label>Placa</label>
              <input [(ngModel)]="nuevoBus.placa" name="placa" required>
            </div>
            <div class="form-group">
              <label>Estado</label>
              <select [(ngModel)]="nuevoBus.estado" name="estado" required>
                <option value="ACTIVO">Activo</option>
                <option value="MANTENIMIENTO">En Mantenimiento</option>
                <option value="INACTIVO">Inactivo</option>
              </select>
            </div>
            <div class="form-group">
              <label>
                <input type="checkbox" [(ngModel)]="nuevoBus.tieneRampa" name="tieneRampa">
                Tiene Rampa
              </label>
            </div>
          </div>
          <button type="submit">Registrar Bus</button>
        </form>
      </div>

      <div class="card">
        <h3>Buses Registrados</h3>
        <div *ngIf="buses.length === 0" class="empty">No hay buses registrados</div>
        <div class="list">
          <div *ngFor="let bus of buses" class="item">
            <div class="item-info">
              <strong>{{ bus.placa }}</strong>
              <span>Estado: {{ bus.estado }}</span>
              <span>{{ bus.tieneRampa ? '♿ Con rampa' : '' }}</span>
            </div>
            <button (click)="eliminarBus(bus.id!)" class="btn-danger">Eliminar</button>
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
    input, select {
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
export class BusesComponent implements OnInit {
  buses: Bus[] = [];
  nuevoBus: Bus = {
    placa: '',
    estado: 'ACTIVO',
    tieneRampa: false
  };

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.cargarBuses();
  }

  cargarBuses() {
    this.apiService.getBuses().subscribe(buses => {
      this.buses = buses;
    });
  }

  crearBus() {
    this.apiService.crearBus(this.nuevoBus).subscribe(() => {
      this.cargarBuses();
      this.nuevoBus = { placa: '', estado: 'ACTIVO', tieneRampa: false };
    });
  }

  eliminarBus(id: number) {
    if (confirm('¿Estás seguro de eliminar este bus?')) {
      this.apiService.eliminarBus(id).subscribe(() => {
        this.cargarBuses();
      });
    }
  }
}

