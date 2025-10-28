import { Routes } from '@angular/router';

export const APP_ROUTES: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadComponent: () => import('./components/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'rutas',
    loadComponent: () => import('./components/rutas/rutas.component').then(m => m.RutasComponent)
  },
  {
    path: 'conductores',
    loadComponent: () => import('./components/conductores/conductores.component').then(m => m.ConductoresComponent)
  },
  {
    path: 'buses',
    loadComponent: () => import('./components/buses/buses.component').then(m => m.BusesComponent)
  }
];

