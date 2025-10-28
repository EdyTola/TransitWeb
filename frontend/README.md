# TransitWeb Frontend

Frontend Angular para el sistema TransitWeb.

## Instalación

```bash
cd frontend
npm install
```

## Ejecutar

```bash
npm start
```

El frontend estará disponible en `http://localhost:4200`

## Estructura

```
frontend/
├── src/
│   ├── app/
│   │   ├── components/        # Componentes organizados por funcionalidad
│   │   │   ├── login/
│   │   │   ├── rutas/
│   │   │   ├── conductores/
│   │   │   └── buses/
│   │   ├── services/         # Servicios de API
│   │   └── app.component.ts
│   └── main.ts
└── package.json
```

## Componentes Creados

- **Login**: Componente de autenticación
- **Rutas**: Gestión de rutas de transporte
- **Conductores**: Gestión de conductores
- **Buses**: Gestión de buses

