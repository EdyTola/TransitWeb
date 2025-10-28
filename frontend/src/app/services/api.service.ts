import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }

  // Login
  login(correo: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/usuarios/login`, { correo, password });
  }

  // Rutas
  getRutas(): Observable<any> {
    return this.http.get(`${this.apiUrl}/rutas`, { headers: this.getHeaders() });
  }

  crearRuta(ruta: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/rutas`, ruta, { headers: this.getHeaders() });
  }

  eliminarRuta(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/rutas/${id}`, { headers: this.getHeaders() });
  }

  // Conductores
  getConductores(): Observable<any> {
    return this.http.get(`${this.apiUrl}/usuarios/rol/conductor`, { headers: this.getHeaders() });
  }

  registrarConductor(conductor: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/usuarios/registrar/conductor`, conductor, { headers: this.getHeaders() });
  }

  eliminarUsuario(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/usuarios/${id}`, { headers: this.getHeaders() });
  }

  // Buses
  getBuses(): Observable<any> {
    return this.http.get(`${this.apiUrl}/buses`, { headers: this.getHeaders() });
  }

  crearBus(bus: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/buses`, bus, { headers: this.getHeaders() });
  }

  eliminarBus(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/buses/${id}`, { headers: this.getHeaders() });
  }
}

